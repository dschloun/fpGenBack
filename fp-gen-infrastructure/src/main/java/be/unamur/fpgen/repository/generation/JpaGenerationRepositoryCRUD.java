package be.unamur.fpgen.repository.generation;

import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.entity.generation.GenerationEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
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

public interface JpaGenerationRepositoryCRUD extends JpaRepository<GenerationEntity, UUID> {
    String COMMON =
        " WHERE (:topic is null or g.topic = :topic) " +
            "AND (:type is null or g.type = :type) " +
            "AND (:authorTrigram is null or g.author.trigram = :authorTrigram) " +
            "AND (:userPrompt is null or lower(g.userPrompt) like %:userPrompt%) " +
            "AND (:systemPrompt is null or lower(g.systemPrompt) like %:userPrompt%) " +
            "AND (:quantity is null or g.quantity <= :quantity) " +
            "AND g.creationDate >= cast(:startDate as timestamp) " +
            "AND g.creationDate <= cast(:endDate as timestamp) " +
            "AND ((:isIn = true AND gd.id IN :datasetIdList) OR (:isIn = false AND gd.id NOT IN :datasetIdList ))";

    @Query(value = "SELECT DISTINCT g from InstantMessageGenerationEntity g " +
            " LEFT JOIN g.instantMessageDatasetList gd" +
             COMMON)
    Page<InstantMessageGenerationEntity> findMessagePagination(
            @Param("topic") MessageTopicEnum topic,
            @Param("type") MessageTypeEnum type,
            @Param("authorTrigram") String authorTrigram,
            @Param("quantity") Integer quantity,
            @Param("userPrompt") String userPrompt,
            @Param("systemPrompt") String systemPrompt,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            @Param("datasetIdList") List<UUID> datasetIdList,
            @Param("isIn") boolean isIn,
            Pageable pageable);

    @Query(value = "SELECT DISTINCT g from ConversationGenerationEntity g " +
            " LEFT JOIN g.conversationDatasetList gd" +
             COMMON)
    Page<ConversationGenerationEntity> findConversationPagination(
            @Param("topic") MessageTopicEnum topic,
            @Param("type") MessageTypeEnum type,
            @Param("authorTrigram") String authorTrigram,
            @Param("quantity") Integer quantity,
            @Param("userPrompt") String userPrompt,
            @Param("systemPrompt") String systemPrompt,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            @Param("datasetIdList") List<UUID> datasetIdList,
            @Param("isIn") boolean isIn,
            Pageable pageable);
}
