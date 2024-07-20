package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface JpaInstantMessageGenerationRepositoryCRUD extends JpaRepository<InstantMessageGenerationEntity, UUID> {
    @Query(value = "SELECT DISTINCT g from InstantMessageGenerationEntity g" +
            " WHERE (:topic is null or g.topic = :topic)" +
            " AND (:type is null or g.type = :type)" +
            " AND (:authorTrigram is null or g.author.trigram = :authorTrigram)" +
            " AND (:userPrompt is null or lower(g.userPrompt) like %:userPrompt%)" +
            " AND (:systemPrompt is null or lower(g.systemPrompt) like %:userPrompt%)" +
            " AND (:quantity is null or g.quantity <= :quantity)" +
            " AND g.creationDate >= cast(:startDate as timestamp)" +
            " AND g.creationDate <= cast(:endDate as timestamp)"
    )
    Page<InstantMessageGenerationEntity> findPagination(@Param("topic") MessageTopicEnum topic,
                                                      @Param("type") MessageTypeEnum type,
                                                      @Param("authorTrigram") String authorTrigram,
                                                      @Param("quantity") Integer quantity,
                                                      @Param("userPrompt") String userPrompt,
                                                      @Param("systemPrompt") String systemPrompt,
                                                      @Param("startDate") OffsetDateTime startDate,
                                                      @Param("endDate") OffsetDateTime endDate,
                                                      Pageable pageable);
}
