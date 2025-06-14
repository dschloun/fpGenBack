package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItemStatus;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.repository.OngoingGenerationRepository;
import be.unamur.model.GenerationCreation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OngoingGenerationService {
    private final AuthorService authorService;
    private final OngoingGenerationRepository ongoingGenerationRepository;

    public OngoingGenerationService(AuthorService authorService, OngoingGenerationRepository ongoingGenerationRepository) {
        this.authorService = authorService;
        this.ongoingGenerationRepository = ongoingGenerationRepository;
    }

    @Transactional
    public OngoingGeneration createOngoingGeneration(GenerationTypeEnum type, UUID datasetId, List<GenerationCreation> generations, Integer min, Integer max) {
        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());
        final Set<OngoingGenerationItem> items = new HashSet<>();
        for(GenerationCreation g: generations){
            items.add(
                    OngoingGenerationItem.newBuilder()
                            .withMessageType(MessageTypeEnum.valueOf(g.getType().name()))
                            .withMessageTopic(MessageTopicEnum.valueOf(g.getTopic().name()))
                            .withQuantity(g.getQuantity())
                            .withStatus(OngoingGenerationItemStatus.WAITING)
                            .withPromptId(g.getPromptId())
                            .build()
            );
        }
        return ongoingGenerationRepository.save(OngoingGeneration.newBuilder()
                .withType(type)
                .withAuthor(author)
                .withStatus(OngoingGenerationStatus.WAITING)
                .withDatasetId(datasetId)
                .withItemList(items)
                .withMinInteractionNumber(min)
                .withMaxInteractionNumber(max)
                .build());
    }

    @Transactional
    public void addItemList(OngoingGeneration ongoingGeneration, List<OngoingGenerationItem> itemList) {
        ongoingGenerationRepository.addItemList(ongoingGeneration, itemList);
    }

    @Transactional
    public void updateStatus(UUID id, OngoingGenerationStatus status) {
        ongoingGenerationRepository.updateStatus(id, status);
    }

    @Transactional
    public OngoingGeneration getOngoingGenerationById(UUID id) {
        return ongoingGenerationRepository.findById(id).orElseThrow();
    }

    @Transactional
    public List<OngoingGeneration> findAllByStatus(OngoingGenerationStatus status) {
        return ongoingGenerationRepository.findAllByStatus(status);
    }

    @Transactional
    public void deleteOngoingGenerationById(UUID id) {
        ongoingGenerationRepository.deleteById(id);
    }
}
