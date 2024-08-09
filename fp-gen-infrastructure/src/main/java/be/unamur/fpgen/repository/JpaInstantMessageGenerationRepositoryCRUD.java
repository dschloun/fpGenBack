package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.entity.view.GenerationProjection;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaInstantMessageGenerationRepositoryCRUD extends JpaRepository<InstantMessageGenerationEntity, UUID> {
    @Query(value = "SELECT DISTINCT g from InstantMessageGenerationEntity g" +
            " LEFT JOIN g.instantMessageDatasetList gd" +
            " WHERE (:topic is null or g.topic = :topic)" +
            " AND (:type is null or g.type = :type)" +
            " AND (:authorTrigram is null or g.author.trigram = :authorTrigram)" +
            " AND (:userPrompt is null or lower(g.userPrompt) like %:userPrompt%)" +
            " AND (:systemPrompt is null or lower(g.systemPrompt) like %:userPrompt%)" +
            " AND (:quantity is null or g.quantity <= :quantity)" +
            " AND g.creationDate >= cast(:startDate as timestamp)" +
            " AND g.creationDate <= cast(:endDate as timestamp)" +
            " AND gd.id NOT IN (:datasetIdList)"
    )
    Page<InstantMessageGenerationEntity> findPagination(@Param("topic") MessageTopicEnum topic,
                                                      @Param("type") MessageTypeEnum type,
                                                      @Param("authorTrigram") String authorTrigram,
                                                      @Param("quantity") Integer quantity,
                                                      @Param("userPrompt") String userPrompt,
                                                      @Param("systemPrompt") String systemPrompt,
                                                      @Param("startDate") OffsetDateTime startDate,
                                                      @Param("endDate") OffsetDateTime endDate,
                                                        @Param("datasetIdList") List<UUID> datasetIdList,
                                                      Pageable pageable);

    //fixme :p is null or ... only works for string, not for UUID, so to counter the problem put a random UUID if datasetList is empty
    // not very clean but it's the only way

    @Query(nativeQuery = true, value =
            "SELECT distinct id as id, " +
                    "creation_date as creationDate, " +
                    "kind as kind, " +
                    "generation_id as generationId, " +
                    "author_trigram as authorTrigram, " +
                    "details as details, " +
                    "user_prompt as userPrompt, " +
                    "topic as topic, " +
                    "type as type, " +
                    "quantity as quantity, " +
                    "dataset_id as datasetId " +
                    "FROM generation_search_view " +
                    "WHERE (:topic is null or topic = :topic) " +
                    "AND (:type is null or type = :type) " +
                    "AND (:authorTrigram is null or author_trigram = :authorTrigram) " +
                    "AND (:userPrompt is null or lower(user_prompt) like %:userPrompt%) " +
                    "AND (:quantity is null or quantity <= :quantity) " +
                    "AND creation_date >= cast(:startDate as timestamp) " +
                    "AND creation_date <= cast(:endDate as timestamp) " +
                    "AND dataset_id NOT IN (:datasetIdList)"
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
