package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.exception.InstantMessageNotFoundException;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.pagination.InstantMessage.InstantMessagesPage;
import be.unamur.fpgen.message.pagination.InstantMessage.PagedInstantMessagesQuery;
import be.unamur.fpgen.mapper.webToDomain.InstantMessageWebToDomainMapper;
import be.unamur.fpgen.repository.InstantMessageRepository;
import be.unamur.fpgen.repository.OngoingGenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.InstantMessageBatchCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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

        // 0. for each
        command.getInstantMessageCreationList().forEach(imc -> {
            // 1. call to chatgpt
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> simulateChatGptCall(imc.getType().name(), imc.getTopic().name(), imc.getQuantity()))
                            .exceptionally(() -> {
                                // 1.1. update ongoing generation status
                               ongoingGenerationRepository.se
                            });



            future.thenApply(result -> {
                // 2. create generation data
                final InstantMessageGeneration generation = instantMessageGenerationService.createInstantMessageGeneration(imc, command.getAuthorId());

            });


            // 2. prepare a list of instant messages
            final List<InstantMessage> instantMessageList = new ArrayList<>();

            // 3. generate instant messages
            //todo call chat gpt api with prompt // return the x messages in json format, unmarshall, ...
            for(int i = 0; i < imc.getQuantity(); i++){
                instantMessageList.add(InstantMessageWebToDomainMapper.mapForCreate(imc, String.format("content %s", i)));
            }
            // 4. save the instant messages
            List<InstantMessage> saved = instantMessageRepository.saveInstantMessageList(instantMessageList, generation);

            // 5. add generation to dataset if needed
            if (Objects.nonNull(command.getDatasetId())) {
                instantMessageDatasetService.addInstantMessageGenerationListToDataset(command.getDatasetId(), List.of(generation.getId()));
            }
        });
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
    private List<String> simulateChatGptCall(String type, String topic, int quantity){
        final List<String> result = new ArrayList<>();
        int i = 0;
        try {
            while(i < quantity){
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
