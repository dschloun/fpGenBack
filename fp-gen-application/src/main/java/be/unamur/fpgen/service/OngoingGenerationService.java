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

/**
 * Service class for managing ongoing generations.
 */
@Service
public class OngoingGenerationService {
    private final AuthorService authorService;
    private final OngoingGenerationRepository ongoingGenerationRepository;

    public OngoingGenerationService(AuthorService authorService, OngoingGenerationRepository ongoingGenerationRepository) {
        this.authorService = authorService;
        this.ongoingGenerationRepository = ongoingGenerationRepository;
    }

    /**
     * Create a new ongoing generation.
     *
     * @param type      the type of generation
     * @param datasetId the dataset id
     * @param generations the list of generations
     * @param min the minimum number of interactions
     * @param max the maximum number of interactions
     * @return the created ongoing generation
     */
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

    /**
     * Add a list of items to an ongoing generation.
     *
     * @param ongoingGeneration the ongoing generation
     * @param itemList          the list of items to add
     */
    @Transactional
    public void addItemList(OngoingGeneration ongoingGeneration, List<OngoingGenerationItem> itemList) {
        ongoingGenerationRepository.addItemList(ongoingGeneration, itemList);
    }

    /**
     * Update the status of an ongoing generation.
     *
     * @param id     the id of the ongoing generation
     * @param status the new status
     */
    @Transactional
    public void updateStatus(UUID id, OngoingGenerationStatus status) {
        ongoingGenerationRepository.updateStatus(id, status);
    }

    /**
     * get the ongoing generation by id.
     * @param id the id of the ongoing generation
     * @return the ongoing generation
     */
    @Transactional
    public OngoingGeneration getOngoingGenerationById(UUID id) {
        return ongoingGenerationRepository.findById(id).orElseThrow();
    }

    /**
     * Find all ongoing generations.
     *
     * @return the list of ongoing generations
     */
    @Transactional
    public List<OngoingGeneration> findAllByStatus(OngoingGenerationStatus status) {
        return ongoingGenerationRepository.findAllByStatus(status);
    }

    /**
     * delete an ongoing generation by id.
     * @param id
     */
    @Transactional
    public void deleteOngoingGenerationById(UUID id) {
        ongoingGenerationRepository.deleteById(id);
    }
}
