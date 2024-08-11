package be.unamur.fpgen.service;

import be.unamur.fpgen.exception.InstantMessageNotFoundException;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItemStatus;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.pagination.InstantMessage.InstantMessagesPage;
import be.unamur.fpgen.message.pagination.InstantMessage.PagedInstantMessagesQuery;
import be.unamur.fpgen.mapper.webToDomain.InstantMessageWebToDomainMapper;
import be.unamur.fpgen.repository.InstantMessageRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.InstantMessageBatchCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class InstantMessageService {

    private final InstantMessageRepository instantMessageRepository;
    private final InstantMessageGenerationService instantMessageGenerationService;
    private final InstantMessageDatasetService instantMessageDatasetService;
    private final OngoingGenerationService ongoingGenerationService;

    public InstantMessageService(final InstantMessageRepository instantMessageRepository,
                                 final InstantMessageGenerationService instantMessageGenerationService,
                                 final InstantMessageDatasetService instantMessageDatasetService,
                                 OngoingGenerationService ongoingGenerationService) {
        this.instantMessageRepository = instantMessageRepository;
        this.instantMessageGenerationService = instantMessageGenerationService;
        this.instantMessageDatasetService = instantMessageDatasetService;
        this.ongoingGenerationService = ongoingGenerationService;
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

        // 3. generation stage
        final List<CompletableFuture<OngoingGenerationItem>> futures = new ArrayList<>();

        command.getInstantMessageCreationList().forEach(imc -> {
            // 0. call to chatgpt
            final CompletableFuture<OngoingGenerationItem> future = CompletableFuture.supplyAsync(() -> simulateChatGptCall(imc.getType().name(), imc.getTopic().name(), imc.getQuantity()))
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
                            .withMessageType(MessageTypeWebToDomainMapper.map(imc.getType()))
                            .withMessageTopic(MessageTopicWebToDomainMapper.map(imc.getTopic()))
                            .withQuantity(imc.getQuantity())
                            .withStatus(OngoingGenerationItemStatus.FAILURE)
                            .build();
                } else {
                    // Generation successful
                    // 1. create generation data
                    final InstantMessageGeneration generation = instantMessageGenerationService.createInstantMessageGeneration(imc, command.getAuthorId());
                    // 2. prepare a list of instant messages
                    final List<InstantMessage> instantMessageList = new ArrayList<>();
                    // 3. generate instant messages
                    for (String s : result) {
                        instantMessageList.add(InstantMessageWebToDomainMapper.mapForCreate(imc, s));
                    }
                    // 4. save the instant messages
                    List<InstantMessage> saved = instantMessageRepository.saveInstantMessageList(instantMessageList, generation);
                    // 5. add generation to dataset if needed
                    if (Objects.nonNull(command.getDatasetId())) {
                        instantMessageDatasetService.addInstantMessageGenerationListToDataset(command.getDatasetId(), List.of(generation.getId()));
                    }
                    // 6. return success item
                    return OngoingGenerationItem.newBuilder()
                            .withMessageType(MessageTypeWebToDomainMapper.map(imc.getType()))
                            .withMessageTopic(MessageTopicWebToDomainMapper.map(imc.getTopic()))
                            .withQuantity(imc.getQuantity())
                            .withStatus(OngoingGenerationItemStatus.SUCCESS)
                            .build();
                }
            });

            // collect futures
            futures.add(future);
        });

        // Wait for all futures to complete
        final CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        // Process the results once all futures are complete
        allOf.thenRun(() -> {
            final List<OngoingGenerationItem> results = futures.stream()
                    .map(CompletableFuture::join) // Block and get result of each future
                    .toList();
            // Handle the accumulated results
            ongoingGenerationService.addItemList(ongoingGeneration, results);
        }).join(); // Block until all futures are done
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

    // chatgpt method
    private List<String> simulateChatGptCall(String type, String topic, int quantity) {
        final List<String> result = new ArrayList<>();
        int i = 0;
        try {
            while (i < quantity) {
                i++;
                result.add(String.format("message %s %s: %s of %s", type, topic, i, quantity));
            }
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
