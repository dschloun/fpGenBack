package be.unamur.fpgen.repository.conversation;


import be.unamur.fpgen.entity.conversation.ConversationEntity;
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

public interface JpaConversationRepositoryCRUD extends JpaRepository<ConversationEntity, UUID>{

    List<ConversationEntity> findAllByConversationGenerationId(UUID generationId);

    @Query(value = "SELECT DISTINCT c from ConversationEntity c" +
            " WHERE (:topic is null or c.topic = :topic)" +
            " AND (:type is null or c.type = :type)" +
            " AND (:minInteractionNumber is null or c.minInteractionNumber >= :minInteractionNumber)" +
            " AND (:maxInteractionNumber is null or c.maxInteractionNumber <= :maxInteractionNumber)" +
            " AND c.creationDate >= cast(:startDate as timestamp)" +
            " AND c.creationDate <= cast(:endDate as timestamp)"
    )
    Page<ConversationEntity> findPagination(@Param("topic") MessageTopicEnum topic,
                                              @Param("type") MessageTypeEnum type,
                                              @Param("minInteractionNumber") Integer minInteractionNumber,
                                              @Param("maxInteractionNumber") Integer maxInteractionNumber,
                                              @Param("startDate") OffsetDateTime startDate,
                                              @Param("endDate") OffsetDateTime endDate,
                                              Pageable pageable);

    boolean existsByHash(String hash);
}
