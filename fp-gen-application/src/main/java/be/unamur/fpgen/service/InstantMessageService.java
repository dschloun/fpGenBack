package be.unamur.fpgen.service;

import be.unamur.fpgen.exception.InstantMessageNotFoundException;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.pagination.InstantMessage.InstantMessagesPage;
import be.unamur.fpgen.message.pagination.InstantMessage.PagedInstantMessagesQuery;
import be.unamur.fpgen.repository.MessageRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.InstantMessageBatchCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service for managing instant messages
 */
@Service
public class InstantMessageService {

    private final MessageRepository messageRepository;
    private final DatasetService datasetService;
    private final OngoingGenerationService ongoingGenerationService;

    public InstantMessageService(final MessageRepository messageRepository,
                                 final DatasetService datasetService,
                                 OngoingGenerationService ongoingGenerationService) {
        this.messageRepository = messageRepository;
        this.datasetService = datasetService;
        this.ongoingGenerationService = ongoingGenerationService;
    }

    /**
     * Generate instant messages
     * @param command the command to generate instant messages
     */
    @Transactional
    public void generateInstantMessages(final InstantMessageBatchCreation command) {
        // 0. create ongoing generation
        final OngoingGeneration ongoingGeneration = ongoingGenerationService.createOngoingGeneration(
                GenerationTypeEnum.INSTANT_MESSAGE, command.getDatasetId(), command.getInstantMessageCreationList(), null, null);

        // 1. if the generation refer to a dataset, then inform the dataset that a generation is pending for him
        if (Objects.nonNull(command.getDatasetId())) {
            datasetService.addOngoingGenerationToDataset(command.getDatasetId(), ongoingGeneration);
        }
    }

    /**
     * Get instant message by id
     * @param instantMessageId the instant message id to get
     * @return the instant message or throw an exception if not found
     */
    @Transactional
    public InstantMessage getInstantMessageById(UUID instantMessageId) {
        return messageRepository.getInstantMessageById(instantMessageId)
                .orElseThrow(() -> InstantMessageNotFoundException.withId(instantMessageId));
    }

    /**
     * search instant messages with pagination
     * @param query
     * @return the instant messages page
     */
    @Transactional
    public InstantMessagesPage searchInstantMessagesPaginate(final PagedInstantMessagesQuery query) {
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        return messageRepository.findPagination(
                query.getInstantMessageQuery().getMessageTopic(),
                query.getInstantMessageQuery().getMessageType(),
                query.getInstantMessageQuery().getContent(),
                DateUtil.ifNullReturnOldDate(query.getInstantMessageQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getInstantMessageQuery().getEndDate()),
                pageable);
    }

    /**
     * delete instant message by id
     * @param instantMessageId
     */
    @Transactional
    public void deleteById(UUID instantMessageId) {
        messageRepository.deleteInstantMessageById(instantMessageId);
    }

    /**
     * find all instant messages by generation id
     * @param generationId
     * @return the list of instant messages
     */
    @Transactional
    public List<InstantMessage> findAllByGenerationId(UUID generationId) {
        return messageRepository.findInstantMessageByGenerationId(generationId);
    }

}
