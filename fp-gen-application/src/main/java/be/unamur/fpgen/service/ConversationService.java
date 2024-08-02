package be.unamur.fpgen.service;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.conversation.pagination.ConversationsPage;
import be.unamur.fpgen.conversation.pagination.PagedConversationsQuery;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.message.ConversationMessage;
import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import be.unamur.fpgen.mapper.webToDomain.ConversationCreationWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.ConversationMessageCreationWebToDomainMapper;
import be.unamur.fpgen.repository.ConversationGenerationRepository;
import be.unamur.fpgen.repository.ConversationMessageRepository;
import be.unamur.fpgen.repository.ConversationRepository;
import be.unamur.fpgen.utils.Alternator;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.fpgen.utils.TypeCorrespondenceMapper;
import be.unamur.model.ConversationBatchCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationGenerationService conversationGenerationService;
    private final InterlocutorService interlocutorService;
    private final ConversationMessageRepository conversationMessageRepository;

    public ConversationService(ConversationRepository conversationRepository,
                               ConversationGenerationService conversationGenerationService,
                               ConversationGenerationRepository conversationGenerationRepository,
                               InterlocutorService interlocutorService, ConversationMessageRepository conversationMessageRepository) {
        this.conversationRepository = conversationRepository;
        this.conversationGenerationService = conversationGenerationService;
        this.interlocutorService = interlocutorService;
        this.conversationMessageRepository = conversationMessageRepository;
    }

    @Transactional
    public List<Conversation> createConversationList(ConversationBatchCreation command) {
        // 2. prepare conversation list
        final List<Conversation> conversationList = new ArrayList<>();

        // 0. for each
        command.getConversationCreationList().forEach(cc -> {
            // 1. create generation data
            final ConversationGeneration generation = conversationGenerationService.createConversationGeneration(cc, command.getAuthorId());

            // 3. generate conversations
            //todo chat gpt api with prompt // return the x messages in json format, unmarshall, ...
            for (int i = 0; i < cc.getQuantity(); i++) {
                // 3.1. save the conversation
                final Conversation conversation = createConversation(generation, ConversationCreationWebToDomainMapper.map(cc, command.getMinInteractionNumber(), command.getMaxInteractionNumber()));

                // 3.2. get interlocutors
                final Interlocutor interlocutor1 = interlocutorService.getRandomInterlocutorByType(TypeCorrespondenceMapper.getCorrespondence(conversation.getType()));
                final Interlocutor interlocutor2 = interlocutorService.getRandomInterlocutorByType(InterlocutorTypeEnum.GENUINE);
                final Alternator<Interlocutor> alternator = new Alternator<>(interlocutor1, interlocutor2);

                // 3.3. generate conversation messages
                List<ConversationMessage> l = new ArrayList<>();
                for (int j = 0; j < conversation.getMaxInteractionNumber(); j++) {
                    l.add(mockConversationMessageGeneration(ConversationMessageCreationWebToDomainMapper.map(cc, alternator.getNext(), alternator.getNext(), conversation.getId()), j));
                }

                // 3.4. save the conversation messages
                final List<ConversationMessage> saved = conversationMessageRepository.saveConversationMessageList(conversation, l);

                // 3.5. add the conversation to the list
                conversationList.add(conversationRepository.getConversationById(conversation.getId()));
            }
        });

        return conversationList;
    }

    @Transactional
    public Conversation createConversation(final ConversationGeneration generation, final Conversation conversation) {
        return conversationRepository.saveConversation(
                Conversation.newBuilder()
                        .withTopic(conversation.getTopic())
                        .withType(conversation.getType())
                        .withMaxInteractionNumber(conversation.getMaxInteractionNumber())
                        .withMinInteractionNumber(conversation.getMinInteractionNumber())
                        .withGenerationId(generation.getId())
                        .build());
    }

    @Transactional
    public Conversation getConversationById(final UUID conversationId) {
        return conversationRepository.getConversationById(conversationId);
    }


    //fixme replace by chatgpt api call
    private ConversationMessage mockConversationMessageGeneration(final ConversationMessage conversationMessage, final int number) {
        return ConversationMessage.newBuilder()
                .withSender(conversationMessage.getSender())
                .withReceiver(conversationMessage.getReceiver())
                .withTopic(conversationMessage.getTopic())
                .withType(conversationMessage.getType())
                .withContent("content SE " + number)
                .build();
    }

    @Transactional
    public ConversationsPage searchConversationsPaginate(final PagedConversationsQuery query){
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize(),
                        Sort.by("type", "topic").ascending());

        return conversationRepository.findPagination(
                query.getConversationQuery().getMessageTopic(),
                query.getConversationQuery().getMessageType(),
                query.getConversationQuery().getMaxInteractionNumber(),
                query.getConversationQuery().getMinInteractionNumber(),
                DateUtil.ifNullReturnOldDate(query.getConversationQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getConversationQuery().getEndDate()),
                pageable);
    }

    @Transactional
    public void deleteConversationById(final UUID conversationId) {
        conversationRepository.deleteConversationById(conversationId);
    }
}
