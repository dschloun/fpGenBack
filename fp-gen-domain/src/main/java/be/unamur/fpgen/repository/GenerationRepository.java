package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.pagination.GenerationPage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for the Generation.
 */
public interface GenerationRepository {

    /**
     * Save a generation.
     * @param generation the generation to save
     * @return the saved generation
     */
    Generation saveGeneration(Generation generation);

    /**
     * Find a generation by its id.
     * @param generationId the id of the generation
     * @return the generation if found empty otherwise
     */
    Optional<Generation> findGenerationById(UUID generationId);

    /**
     * Delete a generation by its id.
     * @param generationId the id of the generation
     */
    void deleteGenerationById(UUID generationId);

    /**
     * Find generations that match the given filters
     * @param type
     * @param messageType
     * @param messageTopic
     * @param promptVersion
     * @param quantity
     * @param authorTrigram
     * @param startDate
     * @param endDate
     * @param datasetIdList
     * @param isIn
     * @param pageable
     * @return a pageable of generations
     */
    GenerationPage findPagination(GenerationTypeEnum type,
                                  MessageTypeEnum messageType,
                                  MessageTopicEnum messageTopic,
                                  Integer promptVersion,
                                  Integer quantity,
                                  String authorTrigram,
                                  OffsetDateTime startDate,
                                  OffsetDateTime endDate,
                                  List<UUID> datasetIdList,
                                  boolean isIn,
                                  Pageable pageable);
}
