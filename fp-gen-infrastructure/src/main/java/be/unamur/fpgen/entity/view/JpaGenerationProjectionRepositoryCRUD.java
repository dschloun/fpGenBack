package be.unamur.fpgen.entity.view;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaGenerationProjectionRepositoryCRUD extends JpaRepository <InstantMessageGenerationEntity, UUID> {

    @Query(nativeQuery = true, value =
    "SELECT distinct id as id, " +
            "creation_date as creationDate, " +
            "kind as kind, " +
            "generation_id as generationId, " +
            "author_id as authorId, " +
            "details as details, " +
            "user_prompt as userPrompt, " +
            "topic as topic, " +
            "type as type, " +
            "quantity as quantity, " +
            "dataset_id as datasetId " +
            "FROM generation_search_view " +
            "WHERE (:topic is null or topic = :topic) " +
            "AND (:type is null or type = :type) " +
            "AND (:authorTrigram is null or authorTrigram = :authorTrigram) " +
            "AND (:userPrompt is null or lower(userPrompt) like %:userPrompt%) " +
            "AND (:quantity is null or quantity <= :quantity) " +
            "AND g.creationDate >= cast(:startDate as timestamp) " +
            "AND g.creationDate <= cast(:endDate as timestamp) " +
            "AND gd.id NOT IN (:datasetIdList)"
    )
    Page<GenerationProjection> search(@Param("topic") String topic,
                                      @Param("type") String type,
                                      @Param("authorTrigram") String authorTrigram,
                                      @Param("quantity") Integer quantity,
                                      @Param("userPrompt") String userPrompt,
                                      @Param("startDate") OffsetDateTime startDate,
                                      @Param("endDate") OffsetDateTime endDate,
                                      @Param("datasetIdList") List<String> datasetIdList,
                                      Pageable pageable);
}
