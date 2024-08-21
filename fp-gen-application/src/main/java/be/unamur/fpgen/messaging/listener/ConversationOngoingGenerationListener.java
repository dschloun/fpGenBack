package be.unamur.fpgen.messaging.listener;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItemStatus;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.message.ConversationMessage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.messaging.event.ConversationOngoingGenerationEvent;
import be.unamur.fpgen.repository.ConversationRepository;
import be.unamur.fpgen.service.DatasetService;
import be.unamur.fpgen.service.GenerationService;
import be.unamur.fpgen.service.InterlocutorService;
import be.unamur.fpgen.service.OngoingGenerationService;
import be.unamur.fpgen.utils.Alternator;
import be.unamur.fpgen.utils.TypeCorrespondenceMapper;
import be.unamur.model.ConversationBatchCreation;
import be.unamur.model.GenerationCreation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class ConversationOngoingGenerationListener {
    private final ConversationRepository conversationRepository;
    private final GenerationService generationService;
    private final DatasetService datasetService;
    private final OngoingGenerationService ongoingGenerationService;
    private final InterlocutorService interlocutorService;

    public ConversationOngoingGenerationListener(ConversationRepository conversationRepository, GenerationService generationService, DatasetService datasetService, OngoingGenerationService ongoingGenerationService, InterlocutorService interlocutorService) {
        this.conversationRepository = conversationRepository;
        this.generationService = generationService;
        this.datasetService = datasetService;
        this.ongoingGenerationService = ongoingGenerationService;
        this.interlocutorService = interlocutorService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateInstantMessages(final ConversationOngoingGenerationEvent event) {
        final ConversationBatchCreation command = event.getCommand();
        final OngoingGeneration ongoingGeneration = ongoingGenerationService.getOngoingGenerationById(event.getOngoingGenerationId());
        // 2. generation stage
        final List<CompletableFuture<OngoingGenerationItem>> futures = new ArrayList<>();

        command.getConversationCreationList().forEach(cc -> {
            // 0. call to chatgpt
            final CompletableFuture<OngoingGenerationItem> future = CompletableFuture.supplyAsync(() -> simulateConversationList(cc, event.getCommand().getMinInteractionNumber(), event.getCommand().getMaxInteractionNumber()))
                    .exceptionally(ex -> {
                        // log
                        System.out.println("Error while generating instant messages");
                        return Collections.emptyList();
                    }).thenApply(result -> {
                        if (result.isEmpty()) {
                            // Failure during generation
                            // 1. update ongoing generation status
                            ongoingGenerationService.updateStatus(ongoingGeneration, OngoingGenerationStatus.PARTIALLY_FAILED);
                            // 2. return failure item
                            return OngoingGenerationItem.newBuilder()
                                    .withMessageType(MessageTypeWebToDomainMapper.map(cc.getType()))
                                    .withMessageTopic(MessageTopicWebToDomainMapper.map(cc.getTopic()))
                                    .withQuantity(cc.getQuantity())
                                    .withStatus(OngoingGenerationItemStatus.FAILURE)
                                    .build();
                        } else {
                            // Generation successful
                            // 1. create generation data
                            final Generation generation = generationService.createGeneration(event.getType(), cc, command.getAuthorId());
                            // 2. prepare a list of instant messages
                            final List<Conversation> conversationList = new ArrayList<>();
                            // 3. generate instant messages
                            conversationList.addAll(result);
                            // 4. save the instant messages
                            List<Conversation> saved = conversationRepository.saveConversationList(conversationList, generation);
                            // 5. add generation to dataset if needed
                            if (Objects.nonNull(command.getDatasetId())) {
                                datasetService.addGenerationListToDataset(command.getDatasetId(), List.of(generation.getId()));
                            }
                            // 6. return success item
                            return OngoingGenerationItem.newBuilder()
                                    .withMessageType(MessageTypeWebToDomainMapper.map(cc.getType()))
                                    .withMessageTopic(MessageTopicWebToDomainMapper.map(cc.getTopic()))
                                    .withQuantity(cc.getQuantity())
                                    .withStatus(OngoingGenerationItemStatus.SUCCESS)
                                    .withGenerationId(generation.getId())
                                    .build();
                        }
                    });

            // collect futures
            futures.add(future);
        });

        // 3. Wait for all futures to complete
        final CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        // 4. Process the results once all futures are complete
        allOf.thenRun(() -> {
                    final List<OngoingGenerationItem> results = futures.stream()
                            .map(CompletableFuture::join) // Block and get result of each future
                            .toList();
                    // 4.1 Handle the accumulated results
                    ongoingGenerationService.addItemList(ongoingGeneration, results);
                    // 4.2 dataset case
                    if (Objects.nonNull(command.getDatasetId())) {
                        datasetService.removeOngoingGenerationFromDataset(command.getDatasetId(), ongoingGeneration);
                        datasetService.addGenerationListToDataset(command.getDatasetId(), results.stream()
                                .filter(ogi -> OngoingGenerationItemStatus.SUCCESS.equals(ogi.getStatus()))
                                .map(OngoingGenerationItem::getGenerationId)
                                .toList());
                    }
                });
    }


    private List<Conversation> simulateConversationList(final GenerationCreation generationCreation, final int minimalInteraction, final int maxInteraction){
        // 0. prepare conversationList
        final List<Conversation> conversationList = new ArrayList<>();

        // 1. for each
        for(int i = 0; i < generationCreation.getQuantity(); i++) {
            conversationList.add(simulateChatGptCall(MessageTypeWebToDomainMapper.map(generationCreation.getType()), MessageTopicWebToDomainMapper.map(generationCreation.getTopic()), minimalInteraction, maxInteraction));
        }

        // 2. return
        return conversationList;
    }

    // chatgpt method
    private Conversation simulateChatGptCall(final MessageTypeEnum type, final MessageTopicEnum topic, final int minimalInteraction, final int maxInteraction) {
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

        // 3.1. save the conversation
        final Conversation conversation =  Conversation.newBuilder()
                .withMinInteractionNumber(minimalInteraction)
                .withMaxInteractionNumber(maxInteraction)
                .withType(type)
                .withTopic(topic)
                .withConversationMessageList(messages)
                .build();

        return conversation;
    }


    private ConversationMessage mockConversationMessageGeneration(final MessageTypeEnum type, final MessageTopicEnum topic, final Interlocutor from, final Interlocutor to, final int number, final int quantity) {
        return ConversationMessage.newBuilder()
                .withSender(from)
                .withReceiver(to)
                .withTopic(topic)
                .withType(type)
                .withContent(String.format("number %s of %s conversation message %s %s: from %s to %s", number, quantity, type, topic, from, to))
                .build();
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public Conversation createConversation(final Generation generation, final Conversation conversation, final Set<ConversationMessage> messageList) {
        return conversationRepository.saveConversation(
                Conversation.newBuilder()
                        .withTopic(conversation.getTopic())
                        .withType(conversation.getType())
                        .withMaxInteractionNumber(conversation.getMaxInteractionNumber())
                        .withMinInteractionNumber(conversation.getMinInteractionNumber())
                        .withGenerationId(generation.getId())
                        .withConversationMessageList(messageList)
                        .build());
    }
}
