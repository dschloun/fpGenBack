package be.unamur.fpgen.service;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.instant_message.ConversationMessage;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import be.unamur.fpgen.mapper.webToDomain.ConversationCreationWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.ConversationMessageCreationWebToDomainMapper;
import be.unamur.fpgen.repository.ConversationGenerationRepository;
import be.unamur.fpgen.repository.ConversationMessageRepository;
import be.unamur.fpgen.repository.ConversationRepository;
import be.unamur.fpgen.repository.InterlocutorRepository;
import be.unamur.model.ConversationBatchCreation;
import be.unamur.model.ConversationCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final SaveGenerationService saveGenerationService;
    private final InterlocutorRepository interlocutorRepository;
    private final ConversationGenerationRepository conversationGenerationRepository;
    private final ConversationMessageRepository conversationMessageRepository;

    public ConversationService(ConversationRepository conversationRepository, SaveGenerationService saveGenerationService, InterlocutorRepository interlocutorRepository, ConversationGenerationRepository conversationGenerationRepository, ConversationMessageRepository conversationMessageRepository) {
        this.conversationRepository = conversationRepository;
        this.saveGenerationService = saveGenerationService;
        this.interlocutorRepository = interlocutorRepository;
        this.conversationGenerationRepository = conversationGenerationRepository;
        this.conversationMessageRepository = conversationMessageRepository;
    }

    @Transactional
    public List<Conversation> createConversationList(ConversationBatchCreation command) {
        // 2. prepare conversation list
        final List<Conversation> conversationList = new ArrayList<>();

        // 0. for each
        command.getConversationCreationList().forEach(cc -> {
            // 1. create generation data
            final ConversationGeneration generation = saveGenerationService.createConversationGeneration(cc);

            // 3. generate conversations
            //todo chat gpt api with prompt // return the x messages in json format, unmarshall, ...
            for (int i = 0; i < cc.getQuantity(); i++) {
                // 3.1. save the conversation
                final Conversation conversation = createConversation(generation, ConversationCreationWebToDomainMapper.map(cc));

                // 3.2. generate conversation messages
                List<ConversationMessage> l = new ArrayList<>();
                for (int j = 0; j < conversation.getMaxInteractionNumber(); j++) {
                    l.add(mockConversationMessageGeneration(ConversationMessageCreationWebToDomainMapper.map(cc), j));
                }
                // 3.3. save the conversation messages
                final List<ConversationMessage> saved = conversationMessageRepository.saveConversationMessageList(conversation, l);
                // 3.4. add the conversation to the list
                conversationList.add(conversationRepository.getConversationById(conversation.getId()));
            }
        });

        return conversationList;
    }


    @Transactional
    public Conversation createConversation(final ConversationGeneration generation, final Conversation conversation) {
        conversationRepository.saveConversation(
                Conversation.newBuilder()
                .withTopic(conversation.getTopic())
                .withType(conversation.getType())
                .withMaxInteractionNumber(conversation.getMaxInteractionNumber())
                        .withGenerationId(generation.getId())
                .build());
        return conversation;
    }


    //fixme replace by chatgpt api call
    private ConversationMessage mockConversationMessageGeneration(final ConversationMessage conversationMessage, final int number) {
        return ConversationMessage.newBuilder()
                .withSender(interlocutorRepository.getRandomInterlocutorByType(InterlocutorTypeEnum.SOCIAL_ENGINEER))
                .withReceiver(interlocutorRepository.getRandomInterlocutorByType(InterlocutorTypeEnum.GENUINE))
                .withTopic(conversationMessage.getTopic())
                .withType(conversationMessage.getType())
                .withContent("content SE " + number)
                .build();
    }
}
