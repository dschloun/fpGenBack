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
                    "prompt_version as promptVersion, " +
                    "topic as topic, " +
                    "type as type, " +
                    "quantity as quantity " +
                    "FROM generation_search_view " +
                    "WHERE :kind = kind " +
                    "AND (:topic is null or topic = :topic) " +
                    "AND (:type is null or type = :type) " +
                    "AND (:authorTrigram is null or lower(author_trigram) like %:authorTrigram%) " +
                    "AND (:promptVersion is null or prompt_version = :promptVersion) " +
                    "AND (:quantity is null or quantity <= :quantity) " +
                    "AND creation_date >= cast(:startDate as timestamp) " +
                    "AND creation_date <= cast(:endDate as timestamp) " +
                    "AND ((:isIn = true AND dataset_id IN :datasetIdList) OR (:isIn = false AND id NOT IN ( " +
                    "SELECT DISTINCT id FROM generation_search_view WHERE dataset_id IN :datasetIdList ))) ",
            countQuery =
                    "SELECT COUNT(DISTINCT id) " +
                            "FROM generation_search_view " +
                            "WHERE :kind = kind " +
                            "AND (:topic is null or topic = :topic) " +
                            "AND (:type is null or type = :type) " +
                            "AND (:authorTrigram is null or lower(author_trigram) = :authorTrigram) " +
                            "AND (:promptVersion is null or prompt_version = :promptVersion) " +
                            "AND (:quantity is null or quantity <= :quantity) " +
                            "AND creation_date >= cast(:startDate as timestamp) " +
                            "AND creation_date <= cast(:endDate as timestamp) " +
                            "AND ((:isIn = true AND dataset_id IN :datasetIdList) OR (:isIn = false AND id NOT IN ( " +
                            "SELECT DISTINCT id FROM generation_search_view WHERE dataset_id IN :datasetIdList ))) "
    )
    Page<GenerationProjection> search(@Param("kind") String kind,
                                      @Param("topic") String topic,
                                      @Param("type") String type,
                                      @Param("authorTrigram") String authorTrigram,
                                      @Param("quantity") Integer quantity,
                                      @Param("promptVersion") Integer promptVersion,
                                      @Param("startDate") OffsetDateTime startDate,
                                      @Param("endDate") OffsetDateTime endDate,
                                      @Param("datasetIdList") List<String> datasetIdList,
                                      @Param("isIn") boolean isIn,
                                      Pageable pageable);
}
