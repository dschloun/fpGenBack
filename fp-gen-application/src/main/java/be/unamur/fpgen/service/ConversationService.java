package be.unamur.fpgen.service;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.conversation.pagination.ConversationsPage;
import be.unamur.fpgen.conversation.pagination.PagedConversationsQuery;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.repository.ConversationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.ConversationBatchCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service class for managing conversations.
 */
@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final OngoingGenerationService ongoingGenerationService;
    private final DatasetService datasetService;

    public ConversationService(ConversationRepository conversationRepository,
                               OngoingGenerationService ongoingGenerationService,
                               DatasetService datasetService) {
        this.conversationRepository = conversationRepository;
        this.ongoingGenerationService = ongoingGenerationService;
        this.datasetService = datasetService;
    }

    /**
     * Generate a list of conversations.
     * @param command the command containing the list of conversations to generate
     */
    @Transactional
    public void generateConversationList(ConversationBatchCreation command) {
        // 0. create ongoing generation
        final OngoingGeneration ongoingGeneration = ongoingGenerationService.createOngoingGeneration(GenerationTypeEnum.CONVERSATION, command.getDatasetId(), command.getConversationCreationList(), command.getMinInteractionNumber(), command.getMaxInteractionNumber());

        // 1. if the generation refer to a dataset, then inform the dataset that a generation is pending for him
        if (Objects.nonNull(command.getDatasetId())) {
            datasetService.addOngoingGenerationToDataset(command.getDatasetId(), ongoingGeneration);
        }
    }

    /**
     * get a conversation by its id
     * @param conversationId
     * @return the conversation
     */
    @Transactional
    public Conversation getConversationById(final UUID conversationId) {
        return conversationRepository.getConversationById(conversationId);
    }

    /**
     * search conversations with pagination
     * @param query
     * @return a page of conversations
     */
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

    /**
     * delete a conversation by its id
     * @param conversationId
     */
    @Transactional
    public void deleteConversationById(final UUID conversationId) {
        conversationRepository.deleteConversationById(conversationId);
    }

    /**
     * find all conversations by generation id
     * @param generationId
     * @return a list of conversations
     */
    @Transactional
    public List<Conversation> findAllByGenerationId(UUID generationId) {
        return conversationRepository.findAllByGenerationId(generationId);
    }
}
