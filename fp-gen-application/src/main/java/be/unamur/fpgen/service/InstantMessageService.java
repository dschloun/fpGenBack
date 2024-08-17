package be.unamur.fpgen.service;

import be.unamur.fpgen.exception.InstantMessageNotFoundException;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.pagination.InstantMessage.InstantMessagesPage;
import be.unamur.fpgen.message.pagination.InstantMessage.PagedInstantMessagesQuery;
import be.unamur.fpgen.messaging.event.OngoingGenerationEvent;
import be.unamur.fpgen.repository.InstantMessageRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.InstantMessageBatchCreation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class InstantMessageService {

    private final InstantMessageRepository instantMessageRepository;
    private final GenerationService generationService;
    private final InstantMessageDatasetService instantMessageDatasetService;
    private final OngoingGenerationService ongoingGenerationService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public InstantMessageService(final InstantMessageRepository instantMessageRepository,
                                 final GenerationService generationService,
                                 final InstantMessageDatasetService instantMessageDatasetService,
                                 OngoingGenerationService ongoingGenerationService, ApplicationEventPublisher applicationEventPublisher) {
        this.instantMessageRepository = instantMessageRepository;
        this.generationService = generationService;
        this.instantMessageDatasetService = instantMessageDatasetService;
        this.ongoingGenerationService = ongoingGenerationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void generateInstantMessages(final InstantMessageBatchCreation command) {
        // 0. create ongoing generation
        final OngoingGeneration ongoingGeneration = ongoingGenerationService.createOngoingGeneration(
                GenerationTypeEnum.INSTANT_MESSAGE, command.getAuthorId());

        // 1. if the generation refer to a dataset, then inform the dataset that a generation is pending for him
        if (Objects.nonNull(command.getDatasetId())) {
            instantMessageDatasetService.addOngoingGenerationToDataset(command.getDatasetId(), ongoingGeneration);
        }

        applicationEventPublisher.publishEvent(new OngoingGenerationEvent(this, ongoingGeneration.getId(), command));
    }

    @Transactional
    public InstantMessage getInstantMessageById(UUID instantMessageId) {
        return instantMessageRepository.getInstantMessageById(instantMessageId)
                .orElseThrow(() -> InstantMessageNotFoundException.withId(instantMessageId));
    }

    @Transactional
    public InstantMessagesPage searchInstantMessagesPaginate(final PagedInstantMessagesQuery query) {
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        return instantMessageRepository.findPagination(
                query.getInstantMessageQuery().getMessageTopic(),
                query.getInstantMessageQuery().getMessageType(),
                query.getInstantMessageQuery().getContent(),
                DateUtil.ifNullReturnOldDate(query.getInstantMessageQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getInstantMessageQuery().getEndDate()),
                pageable);
    }

    @Transactional
    public void deleteById(UUID instantMessageId) {
        instantMessageRepository.deleteInstantMessageById(instantMessageId);
    }

    @Transactional
    public List<InstantMessage> findAllByGenerationId(UUID generationId) {
        return instantMessageRepository.findInstantMessageByGenerationId(generationId);
    }

}
