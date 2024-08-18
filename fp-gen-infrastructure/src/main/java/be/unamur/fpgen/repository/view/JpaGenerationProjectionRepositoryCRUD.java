package be.unamur.fpgen.repository.view;

import be.unamur.fpgen.entity.generation.GenerationEntity;
import be.unamur.fpgen.entity.view.GenerationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaGenerationProjectionRepositoryCRUD extends JpaRepository <GenerationEntity, UUID> {

    @Query(nativeQuery = true, value =
    "SELECT DISTINCT ON (id) id as id, " +
            "creation_date as creationDate, " +
            "kind as kind, " +
            "generation_id as generationId, " +
            "author_trigram as authorTrigram, " +
            "details as details, " +
            "user_prompt as userPrompt, " +
            "topic as topic, " +
            "type as type, " +
            "quantity as quantity " +
            "FROM generation_search_view " +
            "WHERE :kind = kind " +
            "AND (:topic is null or topic = :topic) " +
            "AND (:type is null or type = :type) " +
            "AND (:authorTrigram is null or author_trigram = :authorTrigram) " +
            "AND (:userPrompt is null or lower(user_prompt) like %:userPrompt%) " +
            "AND (:quantity is null or quantity <= :quantity) " +
            "AND creation_date >= cast(:startDate as timestamp) " +
            "AND creation_date <= cast(:endDate as timestamp) " +
            "AND ((:isIn = true AND dataset_id IN :datasetIdList) OR (:isIn = false AND dataset_id NOT IN :datasetIdList OR dataset_id is null)) "
    )
    Page<GenerationProjection> search(@Param("kind") String kind,
                                      @Param("topic") String topic,
                                      @Param("type") String type,
                                      @Param("authorTrigram") String authorTrigram,
                                      @Param("quantity") Integer quantity,
                                      @Param("userPrompt") String userPrompt,
                                      @Param("startDate") OffsetDateTime startDate,
                                      @Param("endDate") OffsetDateTime endDate,
                                      @Param("datasetIdList") List<String> datasetIdList,
                                      @Param("isIn") boolean isIn,
                                      Pageable pageable);
}
