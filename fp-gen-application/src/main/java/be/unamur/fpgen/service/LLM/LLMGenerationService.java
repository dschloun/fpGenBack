package be.unamur.fpgen.service.LLM;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItemStatus;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import be.unamur.fpgen.mapper.webToDomain.InstantMessageWebToDomainMapper;
import be.unamur.fpgen.message.ConversationMessage;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.messaging.event.OngoingGenerationStatusChangeEvent;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.prompt.response.ResponseFormatConverter;
import be.unamur.fpgen.prompt.response.conversation.ConversationResponse;
import be.unamur.fpgen.prompt.response.message.MessageResponse;
import be.unamur.fpgen.repository.ConversationRepository;
import be.unamur.fpgen.repository.MessageRepository;
import be.unamur.fpgen.repository.OngoingGenerationItemRepository;
import be.unamur.fpgen.service.*;
import be.unamur.fpgen.utils.Alternator;
import be.unamur.fpgen.utils.TypeCorrespondenceMapper;
import be.unamur.model.GenerationCreation;
import be.unamur.model.MessageTopic;
import be.unamur.model.MessageType;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service to generate messages and conversations using LLM (Language Learning Model) OpenAI
 */
@Service
public class LLMGenerationService {

    @Value("${simulationLLM}")
    private boolean simulation;
    @Value("${open_ai_api_key}")
    private String openaiApiKey;

    @Value("classpath:promptChatGpt/message_format.json")
    Resource resourceFile;

    private final OngoingGenerationService ongoingGenerationService;
    private final GenerationService generationService;
    private final InterlocutorService interlocutorService;
    private final PromptService promptService;
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final DatasetService datasetService;
    private final OngoingGenerationItemRepository ongoingGenerationItemRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final NotificationService notificationService;


    public LLMGenerationService(final OngoingGenerationService ongoingGenerationService,
                                final GenerationService generationService,
                                final InterlocutorService interlocutorService,
                                final PromptService promptService,
                                final MessageRepository messageRepository,
                                final ConversationRepository conversationRepository,
                                final DatasetService datasetService,
                                final OngoingGenerationItemRepository ongoingGenerationItemRepository,
                                final ApplicationEventPublisher eventPublisher,
                                final NotificationService notificationService) {
        this.ongoingGenerationService = ongoingGenerationService;
        this.generationService = generationService;
        this.interlocutorService = interlocutorService;
        this.promptService = promptService;
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.datasetService = datasetService;
        this.ongoingGenerationItemRepository = ongoingGenerationItemRepository;
        this.eventPublisher = eventPublisher;
        this.notificationService = notificationService;
    }

    /**
     * Generate messages and conversations for ongoing generations
     */
    @Transactional
    public void generate() {

        // 1. check if there are any ongoing generations
        final List<OngoingGeneration> ongoingGenerations = ongoingGenerationService.findAllByStatus(OngoingGenerationStatus.WAITING);
        if (ongoingGenerations.isEmpty()) {
            return;
        }

        // 2. start the task
        for (OngoingGeneration o : ongoingGenerations) {

            // 1. change the status of the ongoing generation
            o.updateStatus(OngoingGenerationStatus.ONGOING);

            // 2. generation
            generation(o);

            // 3. send notification
            notificationService.create(o.getAuthor().getId(), generateNotificationMessage(o));
        }
    }

    /**
     * Generate messages and conversations for a specific ongoing generation
     *
     * @param ongoingGeneration the ongoing generation
     */
    private void generation(final OngoingGeneration ongoingGeneration) {

        // 0. memorise generation list initial size
        final int generationListInitialSize = ongoingGeneration.getItemList().size();
        final UUID datasetId = ongoingGeneration.getDatasetId();

        // 1. for each generation item
        for (OngoingGenerationItem item : ongoingGeneration.getItemList()) {
            // 1.0. get prompt
            final DatasetTypeEnum datasetType = DatasetTypeEnum.valueOf(ongoingGeneration.getType().name());
            Prompt prompt = getPrompt(item, datasetType);

            // 1.0. create generation
            final GenerationCreation command = new GenerationCreation()
                    .quantity(item.getQuantity())
                    .type(MessageType.valueOf(item.getMessageType().name()))
                    .topic(MessageTopic.valueOf(item.getMessageTopic().name()));

            final Generation generation = generationService.createGeneration(ongoingGeneration.getType(), command, prompt, ongoingGeneration.getAuthor());

            if (GenerationTypeEnum.INSTANT_MESSAGE.equals(ongoingGeneration.getType())) {
                messageTreatment(item, prompt, command, generation, datasetId);
            } else {
                conversationTreatment(item, ongoingGeneration.getMinInteractionNumber(), ongoingGeneration.getMaxInteractionNumber(), prompt, generation, datasetId);
            }
        }

        // 2. delete item if fully succeeded
        final List<OngoingGenerationItem> successItems = ongoingGeneration.getItemList()
                .stream()
                .filter(item -> OngoingGenerationItemStatus.SUCCESS.equals(item.getStatus()))
                .toList();

        final List<UUID> idsToDelete = successItems.stream().map(BaseUuidDomain::getId).toList();

        // 3. adapt status or delete generation
        if (successItems.size() == generationListInitialSize) {
            ongoingGenerationService.deleteOngoingGenerationById(ongoingGeneration.getId());
        } else {
            if (!successItems.isEmpty() && successItems.size() < generationListInitialSize) {
                eventPublisher.publishEvent(new OngoingGenerationStatusChangeEvent(this, ongoingGeneration.getId(), OngoingGenerationStatus.PARTIALLY_FAILED, idsToDelete));
            } else {
                eventPublisher.publishEvent(new OngoingGenerationStatusChangeEvent(this, ongoingGeneration.getId(), OngoingGenerationStatus.FAILED, idsToDelete));
            }
        }
    }

    /**
     * Get the prompt to use for the generation
     * @param item        the generation item
     * @param datasetType the dataset type
     * @return the prompt
     */
    private Prompt getPrompt(OngoingGenerationItem item, DatasetTypeEnum datasetType) {
        Prompt prompt;
        if (Objects.nonNull(item.getPromptId())) {
            try {
                prompt = promptService.findById(item.getPromptId());
            } catch(Exception e) {
                System.out.println("prompt id do not exist select default prompt");
                prompt = promptService.getDefaultPrompt(datasetType, item.getMessageType());
            }
            //check coherence
            if (!datasetType.equals(prompt.getDatasetType()) || !item.getMessageType().equals(prompt.getMessageType())){
                System.out.println("an incoherence prompt has been found select default one for type");
                prompt = promptService.getDefaultPrompt(datasetType, item.getMessageType());
            }
        } else {
            // no pomptId in parameter take the default one
            prompt = promptService.getDefaultPrompt(datasetType, item.getMessageType());
        }
        return prompt;
    }

    /**
     * Generate messages for a specific generation item (message)
     * @param item    the generation item
     * @param prompt  the prompt
     * @param command the generation creation command
     * @param generation the generation
     * @param datasetId the dataset id
     */
    private void messageTreatment(OngoingGenerationItem item, Prompt prompt, GenerationCreation command, Generation generation, UUID datasetId) {
        // 1.1. prepare message list
        final List<InstantMessage> instantMessageList = new ArrayList<>();
        int tryCounter = 3; //limit the number of try when failed or duplicated messages

        // 1.2. generation
        while (tryCounter > 0 && item.getQuantity() > 0) {
            //1.2.0. init a list of content
            List<String> messages = new ArrayList<>();

            // 1.2.1. generate with LLM
            try {
                if (simulation) {
                    messages = simulateLLMCallMessage(item.getMessageType().name(), item.getMessageTopic().name(), item.getQuantity());
                } else {
                    messages = openAiGenerateMessages(item.getMessageTopic(), item.getQuantity(), prompt);
                }
            } catch (Exception e) {
                tryCounter--;
                System.out.println("Error joining CHAT-GPT");
            }

            // 1.2.2. create messages objects (check if duplicated)
            boolean hasDuplicated = false;

            for (String s : messages) {
                String hash = generateSHA256(s);
                InstantMessage message = InstantMessageWebToDomainMapper.mapForCreate(command, s, hash);
                // check if hash already exist
                if (simulation) {
                    instantMessageList.add(message);
                    item.decrementQuantity();
                } else {
                    boolean alreadyExist = messageRepository.existByHash(message.getHash());

                    if (!alreadyExist) {
                        instantMessageList.add(message);
                        item.decrementQuantity();
                    } else {
                        hasDuplicated = true;
                    }
                }
            }

            // 1.2.3. decrement try number if duplicate
            if (hasDuplicated) {
                tryCounter--;
            }
        }

        // 1.3. set generation item status
        if (item.getQuantity() > 0) {
            item.updateStatus(OngoingGenerationItemStatus.FAILURE);
            ongoingGenerationItemRepository.updateStatus(item.getId(), OngoingGenerationItemStatus.FAILURE);
        } else {
            item.updateStatus(OngoingGenerationItemStatus.SUCCESS);
            ongoingGenerationItemRepository.updateStatus(item.getId(), OngoingGenerationItemStatus.SUCCESS);
        }

        // 1.4. persist messages
        if (instantMessageList.isEmpty()) {
            generationService.deleteGenerationById(generation.getId());
        } else {
            messageRepository.saveInstantMessageList(instantMessageList, generation);
            // add generation to dataset if needed
            if (Objects.nonNull(datasetId)) {
                datasetService.addGenerationListToDataset(datasetId, List.of(generation.getId()));
            }
        }
    }

    /**
     * Simulate a call to LLM to generate messages (during development)
     * @param type     the message type
     * @param topic    the message topic
     * @param quantity the quantity
     * @return the list of messages
     */
    private List<String> simulateLLMCallMessage(String type, String topic, int quantity) {
        final List<String> result = new ArrayList<>();
        int i = 0;
        try {
            while (i < quantity) {
                i++;
                result.add(String.format("message %s %s: %s of %s", type, topic, i, quantity));
            }
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Generate conversations for a specific generation item (conversation)
     * @param item    the generation item
     * @param min     the minimal number of interactions
     * @param max     the maximal number of interactions
     * @param prompt  the prompt
     * @param generation the generation
     * @param datasetId the dataset id
     */
    private void conversationTreatment(OngoingGenerationItem item, Integer min, Integer max, Prompt prompt, Generation generation, UUID datasetId) {
        // 1.1. prepare message list
        final List<Conversation> conversationList = new ArrayList<>();
        int tryCounter = 3; //limit the number of try when failed or duplicated messages

        // 1.2. generation
        while (tryCounter > 0 && item.getQuantity() > 0) {
            //1.2.0. init a list of content
            List<Conversation> conversationTempList = new ArrayList<>();

            // 1.2.1. generate with LLM
            try {
                if (simulation) {
                    conversationTempList = simulateLLMCallConversationList(item.getMessageType(), item.getMessageTopic(), min, max, prompt, item.getQuantity());
                } else {
                    conversationTempList = openAiGenerateConversations(item.getMessageType(), item.getMessageTopic(), min, max, prompt, item.getQuantity());
                }
            } catch (Exception e) {
                tryCounter--;
                System.out.println("Error joining LLM");
            }

            // 1.2.2. create messages objects (check if duplicated)
            boolean hasDuplicated = false;

            for (Conversation conv : conversationTempList) {
                // check if hash already exist
                if (simulation) {
                    conversationRepository.saveConversation(conv);
                    item.decrementQuantity();
                } else {
                    boolean alreadyExist = conversationRepository.existsByHash(conv.getHash());

                    if (!alreadyExist) {
                        conversationList.add(conv);
                        item.decrementQuantity();
                    } else {
                        hasDuplicated = true;
                    }
                }
            }
            // 1.2.3. decrement try number if duplicate
            if (hasDuplicated) {
                tryCounter--;
            }
        }

        // 1.3. set generation item status
        if (item.getQuantity() > 0) {
            item.updateStatus(OngoingGenerationItemStatus.FAILURE);
            ongoingGenerationItemRepository.updateStatus(item.getId(), OngoingGenerationItemStatus.FAILURE);
        } else {
            item.updateStatus(OngoingGenerationItemStatus.SUCCESS);
            ongoingGenerationItemRepository.updateStatus(item.getId(), OngoingGenerationItemStatus.SUCCESS);
        }

        // 1.4. persist messages
        if (conversationList.isEmpty()) {
            generationService.deleteGenerationById(generation.getId());
        } else {
            conversationRepository.saveConversationList(conversationList, generation);
            // add generation to dataset if needed
            if (Objects.nonNull(datasetId)) {
                datasetService.addGenerationListToDataset(datasetId, List.of(generation.getId()));
            }
        }
    }

    /**
     * Simulate a call to LLM to generate conversations (during development)
     * @param type     the message type
     * @param topic    the message topic
     * @param minimalInteraction the minimal number of interactions
     * @param maxInteraction the maximal number of interactions
     * @param prompt   the prompt
     * @param quantity the quantity
     * @return the list of conversations
     */
    private List<Conversation> simulateLLMCallConversationList(final MessageTypeEnum type, final MessageTopicEnum topic, final int minimalInteraction, final int maxInteraction, final Prompt prompt, final Integer quantity) {
        List<Conversation> conversationList = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            conversationList.add(simulateLLMCallConversation(type, topic, minimalInteraction, maxInteraction, prompt));
        }

        return conversationList;
    }

    /**
     * Simulate a call to LLM to generate a conversation (during development)
     * @param type     the message type
     * @param topic    the message topic
     * @param minimalInteraction the minimal number of interactions
     * @param maxInteraction the maximal number of interactions
     * @param prompt   the prompt
     * @return the conversation
     */
    private Conversation simulateLLMCallConversation(final MessageTypeEnum type, final MessageTopicEnum topic, final int minimalInteraction, final int maxInteraction, final Prompt prompt) {
        // 0. simulate interlocutor
        final Interlocutor interlocutor1 = interlocutorService.getRandomInterlocutorByType(TypeCorrespondenceMapper.getCorrespondence(type));
        final Interlocutor interlocutor2 = interlocutorService.getRandomInterlocutorByType(InterlocutorTypeEnum.GENUINE);
        final Alternator<Interlocutor> alternator = new Alternator<>(interlocutor1, interlocutor2);

        // 1. generate messages
        final int quantity = getRandomNumberInRange(minimalInteraction, maxInteraction);
        final Set<ConversationMessage> messages = new HashSet<>();
        int i = 0;
        try {
            while (i < quantity) {
                i++;
                messages.add(mockConversationMessageGeneration(type, topic, alternator.getNext(), alternator.getNext(), i, quantity));
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final String hashString = generateSHA256(
                type.name()
                        + topic.name()
                        + messages
                        .stream()
                        .map(c -> c.getOrderNumber() + c.getContent())
                        .collect(Collectors.joining()));

        // 3.1. save the conversation
        final Conversation conversation = Conversation.newBuilder()
                .withMinInteractionNumber(minimalInteraction)
                .withMaxInteractionNumber(maxInteraction)
                .withType(type)
                .withTopic(topic)
                .withConversationMessageList(messages)
                .withHash(hashString)
                .build();

        return conversation;
    }

    /**
     * Mock a conversation message
     * @param type     the message type
     * @param topic    the message topic
     * @param from     the sender
     * @param to       the receiver
     * @param number   the order number
     * @param quantity the quantity
     * @return the conversation message
     */
    private ConversationMessage mockConversationMessageGeneration(final MessageTypeEnum type, final MessageTopicEnum topic, final Interlocutor from, final Interlocutor to, final int number, final int quantity) {
        return ConversationMessage.newBuilder()
                .withSender(from)
                .withReceiver(to)
                .withTopic(topic)
                .withType(type)
                .withContent(String.format("number %s of %s conversation message %s %s: from %s to %s", number, quantity, type, topic, from.getId(), to.getId()))
                .withOrderNumber(number)
                .build();
    }

    /**
     * Generate a random number in a range
     * @param min the minimal value
     * @param max the maximal value
     * @return the random number
     */
    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Generate a SHA-256 hash
     * @param input the input
     * @return the hash
     */
    public static String generateSHA256(String input) {
        try {
            // Create a SHA-256 instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Apply the SHA-256 algorithm to the input converted to bytes (UTF-8 encoding).
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert the bytes to a hexadecimal string
            StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();  // Return the hash as a hexadecimal string

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error : Algorithm SHA-256 not found", e);
        }
    }

    /**
     * Generate a notification message
     * @param ongoingGeneration the ongoing generation
     * @return the notification message
     */
    private String generateNotificationMessage(OngoingGeneration ongoingGeneration) {
        return "Hello " + ongoingGeneration.getAuthor().getFirstName() + " " + ongoingGeneration.getAuthor().getLastName() + ". Your generation from " + ongoingGeneration.getCreationDate() + " from type " + ongoingGeneration.getType() + " is done";
    }

    /**
     * Generate messages using LLM (Language Learning Model) OpenAI
     * @param topic    the message topic
     * @param quantity the quantity
     * @param prompt   the prompt
     * @return the list of messages
     * @throws IOException if an error occurs
     */
    private List<String> openAiGenerateMessages(MessageTopicEnum topic, Integer quantity, Prompt prompt) throws IOException {
        // Load message format from JSON file
        //get content file as string

        // define the model
        final ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(openaiApiKey)
                .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                .strictTools(true)
                .responseFormat("json_object")
                .strictJsonSchema(true)
                .build();

        final SystemMessage systemMessage = SystemMessage.from(
                prompt.getSystemPrompt()
        );

        final UserMessage userMessage = UserMessage.from(
                TextContent.from(prompt.replacePlaceholder(quantity, null, null, topic))
        );

        final Response<AiMessage> response = model.generate(systemMessage, userMessage);

        // convert response into response object
        final MessageResponse messageResponse = ResponseFormatConverter.messageFromJson(response.content().text());

        // return
        List<String> messages = new ArrayList<>();
        messageResponse.getGenerations().forEach(generation -> {
            messages.add(generation.getMessage());
        });

        return messages;
    }

    /**
     * Generate conversations using LLM (Language Learning Model) OpenAI
     * @param type            the message type
     * @param topic           the message topic
     * @param minInteraction  the minimal number of interactions
     * @param maxInteraction  the maximal number of interactions
     * @param prompt          the prompt
     * @param quantity        the quantity
     * @return the list of conversations
     * @throws IOException if an error occurs
     */
    private List<Conversation> openAiGenerateConversations(final MessageTypeEnum type, final MessageTopicEnum topic, final int minInteraction, final int maxInteraction, final Prompt prompt, final Integer quantity) throws IOException {
        // Load message format from JSON file
        //get content file as string

        // define the model
        final ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(openaiApiKey)
                .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                .strictTools(true)
                .responseFormat("json_object")
                .strictJsonSchema(true)
                .build();

        final SystemMessage systemMessage = SystemMessage.from(
                prompt.getSystemPrompt()
        );

        final UserMessage userMessage = UserMessage.from(
                TextContent.from(prompt.replacePlaceholder(quantity, minInteraction, maxInteraction, topic))
        );

        final Response<AiMessage> response = model.generate(systemMessage, userMessage);

        // convert response into response object
        final ConversationResponse conversationResponse = ResponseFormatConverter.conversationFromJson(response.content().text());

        // return
        List<Conversation> conversations = new ArrayList<>();
        // prepare conversation messages

        conversationResponse.getGenerations().forEach(conversation -> {
            conversations.add(Conversation.newBuilder()
                    .withType(type)
                    .withTopic(topic)
                    .withMaxInteractionNumber(maxInteraction)
                    .withMinInteractionNumber(minInteraction)
                    .withConversationMessageList(conversation.getMessages()
                            .stream()
                            .map(c -> buildConversationMessage(c, type, topic, Integer.parseInt(c.getMessageOrder())))
                            .collect(Collectors.toSet()))
                    .withHash(generateSHA256(
                            type.name()
                                    + topic.name()
                                    + conversation.getMessages()
                                    .stream()
                                    .map(c -> c.getMessageOrder() + c.getContent())
                                    .collect(Collectors.joining())))
                    .build());
        });

        return conversations;
    }

    /**
     * Build a conversation message
     * @param message    the message
     * @param type       the message type
     * @param topic      the message topic
     * @param orderNumber the order number
     * @return the conversation message
     */
    private ConversationMessage buildConversationMessage(be.unamur.fpgen.prompt.response.conversation.ConversationMessage message, MessageTypeEnum type, MessageTopicEnum topic, int orderNumber) {
        final Interlocutor sender;
        if(MessageTypeEnum.GENUINE.equals(type)){
            if(orderNumber % 2 == 0){
                sender = interlocutorService.getGenuineInterlocutor2();
            } else {
                sender = interlocutorService.getGenuineInterlocutor1();
            }
        } else {
            if(message.getActorType().equals("GENUINE")){
                sender = interlocutorService.getGenuineInterlocutor1();
            } else {
                sender = interlocutorService.getInterlocutorByType(InterlocutorTypeEnum.valueOf(message.getActorType()));
            }
        }
        final Interlocutor receiver;
        // determine receiver depending of sender type
        if (sender.getType().equals(InterlocutorTypeEnum.GENUINE) && type.equals(MessageTypeEnum.SOCIAL_ENGINEERING)) {
            receiver = interlocutorService.getInterlocutorByType(InterlocutorTypeEnum.SOCIAL_ENGINEER);
        } else if (sender.getType().equals(InterlocutorTypeEnum.GENUINE) && type.equals(MessageTypeEnum.HARASSMENT)) {
            receiver = interlocutorService.getInterlocutorByType(InterlocutorTypeEnum.HARASSER);
        } else {
            if (Objects.isNull(sender.getNumber())){
                receiver = interlocutorService.getGenuineInterlocutor1();
            } else {
                if (sender.getNumber().equals(1)) {
                    receiver = interlocutorService.getGenuineInterlocutor2();
                } else {
                    receiver = interlocutorService.getGenuineInterlocutor1();
                }
            }
        }
        return ConversationMessage.newBuilder()
                .withType(type)
                .withTopic(topic)
                .withOrderNumber(orderNumber)
                .withSender(sender)
                .withReceiver(receiver)
                .withOrderNumber(Integer.valueOf(message.getMessageOrder()))
                .withContent(message.getContent())
                .build();
    }

}
