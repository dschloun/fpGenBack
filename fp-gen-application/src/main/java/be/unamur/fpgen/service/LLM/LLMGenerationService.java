package be.unamur.fpgen.service.LLM;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import be.unamur.fpgen.mapper.webToDomain.InstantMessageWebToDomainMapper;
import be.unamur.fpgen.message.ConversationMessage;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.service.GenerationService;
import be.unamur.fpgen.service.InterlocutorService;
import be.unamur.fpgen.service.OngoingGenerationService;
import be.unamur.fpgen.service.PromptService;
import be.unamur.fpgen.utils.Alternator;
import be.unamur.fpgen.utils.TypeCorrespondenceMapper;
import be.unamur.model.GenerationCreation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class LLMGenerationService {


    private final boolean simulation;

    private final TaskStatus taskStatus;
    private final OngoingGenerationService ongoingGenerationService;
    private final GenerationService generationService;
    private final InterlocutorService interlocutorService;
    private final PromptService promptService;

    public LLMGenerationService(@Value("${simulation}") boolean simulation, final TaskStatus taskStatus,
                                final OngoingGenerationService ongoingGenerationService,
                                final GenerationService generationService,
                                final InterlocutorService interlocutorService,
                                final PromptService promptService) {
        this.simulation = simulation;
        this.taskStatus = taskStatus;
        this.ongoingGenerationService = ongoingGenerationService;
        this.generationService = generationService;
        this.interlocutorService = interlocutorService;
        this.promptService = promptService;
    }

    @Transactional
    public void generate(){
        // 0. check if a task is currently running
        if(taskStatus.isRunning()){
            return;
        }

        // 1. check if there are any ongoing generations
        final List<OngoingGeneration> ongoingGenerations = ongoingGenerationService.findAllByStatus(OngoingGenerationStatus.WAITING);
        if(ongoingGenerations.isEmpty()){
            return;
        }

        // 2. start the task
        for(OngoingGeneration o : ongoingGenerations){
            // 1. change the status of the ongoing generation
            o.updateStatus(OngoingGenerationStatus.ONGOING);

            // 2. generate each item
            for(OngoingGenerationItem item : o.getItemList()){
                // 1. check message or conversation
                if (GenerationTypeEnum.INSTANT_MESSAGE.equals(o.getType())){

                }
            }
        }



    }

    // chatgpt method
    private void generateMessages(final OngoingGeneration ongoingGeneration){
        for(OngoingGenerationItem im : ongoingGeneration.getItemList()){
            final GenerationCreation command = new GenerationCreation().promptVersion()
            final Generation generation = generationService.createGeneration(GenerationTypeEnum.INSTANT_MESSAGE, im);
            // 1. get text
            List<String> messages = simulateChatGptCallMessage(im.getMessageType().name(), im.getMessageTopic().name(), im.getQuantity());

            // 2. create messages objects
            final List<InstantMessage> instantMessageList = new ArrayList<>();
            for (String s : messages) {
                instantMessageList.add(InstantMessageWebToDomainMapper.mapForCreate(im, s));
            }
        }

    }

    private List<String> simulateChatGptCallMessage(String type, String topic, int quantity) {
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

    // chatgpt simulation methods conversation
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
                .withContent(String.format("number %s of %s conversation message %s %s: from %s to %s", number, quantity, type, topic, from.getId(), to.getId()))
                .withOrderNumber(number)
                .build();
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static String generateSHA256(String input) {
        try {
            // Crée une instance de l'algorithme SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Applique l'algorithme SHA-256 sur l'entrée convertie en bytes (encodage UTF-8)
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convertit les bytes en une chaîne hexadécimale
            StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();  // Renvoie le hash sous forme de chaîne hexadécimale

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur : Algorithme SHA-256 non trouvé", e);
        }
    }
}
