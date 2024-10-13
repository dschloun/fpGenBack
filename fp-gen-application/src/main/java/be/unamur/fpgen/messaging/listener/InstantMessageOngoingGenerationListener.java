//package be.unamur.fpgen.messaging.listener;
//
//import be.unamur.fpgen.generation.Generation;
//import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
//import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
//import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItemStatus;
//import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
//import be.unamur.fpgen.mapper.webToDomain.InstantMessageWebToDomainMapper;
//import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
//import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
//import be.unamur.fpgen.message.InstantMessage;
//import be.unamur.fpgen.messaging.event.InstantMessageOngoingGenerationEvent;
//import be.unamur.fpgen.repository.MessageRepository;
//import be.unamur.fpgen.service.DatasetService;
//import be.unamur.fpgen.service.GenerationService;
//import be.unamur.fpgen.service.OngoingGenerationService;
//import be.unamur.model.InstantMessageBatchCreation;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.event.TransactionPhase;
//import org.springframework.transaction.event.TransactionalEventListener;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.CompletableFuture;
//
//@Component
//public class InstantMessageOngoingGenerationListener {
//    private final MessageRepository messageRepository;
//    private final GenerationService generationService;
//    private final DatasetService datasetService;
//    private final OngoingGenerationService ongoingGenerationService;
//
//    public InstantMessageOngoingGenerationListener(MessageRepository messageRepository, GenerationService generationService, DatasetService datasetService, OngoingGenerationService ongoingGenerationService) {
//        this.messageRepository = messageRepository;
//        this.generationService = generationService;
//        this.datasetService = datasetService;
//        this.ongoingGenerationService = ongoingGenerationService;
//    }
//
//    @Async
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void generateInstantMessages(final InstantMessageOngoingGenerationEvent event) {
//        final InstantMessageBatchCreation command = event.getCommand();
//        final OngoingGeneration ongoingGeneration = ongoingGenerationService.getOngoingGenerationById(event.getOngoingGenerationId());
//        // 2. generation stage
//        final List<CompletableFuture<OngoingGenerationItem>> futures = new ArrayList<>();
//
//        command.getInstantMessageCreationList().forEach(imc -> {
//            // 0. call to chatgpt
//            final CompletableFuture<OngoingGenerationItem> future = CompletableFuture.supplyAsync(() -> simulateChatGptCall(imc.getType().name(), imc.getTopic().name(), imc.getQuantity()))
//                    .exceptionally(ex -> {
//                        // log
//                        System.out.println("Error while generating instant messages");
//                        return Collections.emptyList();
//                    }).thenApply(result -> {
//                        if (result.isEmpty()) {
//                            // Failure during generation
//                            // 1. update ongoing generation status
//                            ongoingGenerationService.updateStatus(ongoingGeneration, OngoingGenerationStatus.PARTIALLY_FAILED);
//                            // 2. return failure item
//                            return OngoingGenerationItem.newBuilder()
//                                    .withMessageType(MessageTypeWebToDomainMapper.map(imc.getType()))
//                                    .withMessageTopic(MessageTopicWebToDomainMapper.map(imc.getTopic()))
//                                    .withQuantity(imc.getQuantity())
//                                    .withStatus(OngoingGenerationItemStatus.FAILURE)
//                                    .build();
//                        } else {
//                            // Generation successful
//                            // 1. create generation data
//                            final Generation generation = generationService.createGeneration(event.getType(), imc, null, null);
//                            // 2. prepare a list of instant messages
//                            final List<InstantMessage> instantMessageList = new ArrayList<>();
//                            // 3. generate instant messages
//                            for (String s : result) {
//                                instantMessageList.add(InstantMessageWebToDomainMapper.mapForCreate(imc, s, null));
//                            }
//                            // 4. save the instant messages
//                            List<InstantMessage> saved = messageRepository.saveInstantMessageList(instantMessageList, generation);
//                            // 5. add generation to dataset if needed
//                            if (Objects.nonNull(command.getDatasetId())) {
//                                datasetService.addGenerationListToDataset(command.getDatasetId(), List.of(generation.getId()));
//                            }
//                            // 6. return success item
//                            return OngoingGenerationItem.newBuilder()
//                                    .withMessageType(MessageTypeWebToDomainMapper.map(imc.getType()))
//                                    .withMessageTopic(MessageTopicWebToDomainMapper.map(imc.getTopic()))
//                                    .withQuantity(imc.getQuantity())
//                                    .withStatus(OngoingGenerationItemStatus.SUCCESS)
//                                    .withGenerationId(generation.getId())
//                                    .build();
//                        }
//                    });
//
//            // collect futures
//            futures.add(future);
//        });
//
//        // 3. Wait for all futures to complete
//        final CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//
//        // 4. Process the results once all futures are complete
//        allOf.thenRun(() -> {
//                    final List<OngoingGenerationItem> results = futures.stream()
//                            .map(CompletableFuture::join) // Block and get result of each future
//                            .toList();
//                    // 4.1 Handle the accumulated results
//                    ongoingGenerationService.addItemList(ongoingGeneration, results);
//                    // 4.2 dataset case
//                    if (Objects.nonNull(command.getDatasetId())) {
//                        //datasetService.removeOngoingGenerationFromDataset(command.getDatasetId());
//                        datasetService.addGenerationListToDataset(command.getDatasetId(), results.stream()
//                                .filter(ogi -> OngoingGenerationItemStatus.SUCCESS.equals(ogi.getStatus()))
//                                .map(OngoingGenerationItem::getGenerationId)
//                                .toList());
//                    }
//                });
//    }
//
//    // chatgpt method
//    private List<String> simulateChatGptCall(String type, String topic, int quantity) {
//        final List<String> result = new ArrayList<>();
//        int i = 0;
//        try {
//            while (i < quantity) {
//                i++;
//                result.add(String.format("message %s %s: %s of %s", type, topic, i, quantity));
//            }
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        return result;
//    }
//}
