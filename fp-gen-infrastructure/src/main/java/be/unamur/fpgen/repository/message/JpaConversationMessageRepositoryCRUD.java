package be.unamur.fpgen.repository.message;

import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface JpaConversationMessageRepositoryCRUD extends JpaRepository<ConversationInstantMessageEntity, UUID>{
    @Query(value = "SELECT DISTINCT i from ConversationInstantMessageEntity i" +
            " WHERE (:topic is null or i.topic = :topic)" +
            " AND (:type is null or i.type = :type)" +
            " AND (:content is null or lower(i.content) like %:content%)" +
            " AND i.creationDate >= cast(:startDate as timestamp)" +
            " AND i.creationDate <= cast(:endDate as timestamp)"
    )
    Page<ConversationInstantMessageEntity> findPagination(@Param("topic") MessageTopicEnum topic,
                                              @Param("type") MessageTypeEnum type,
                                              @Param("content") String content,
                                              @Param("startDate") OffsetDateTime startDate,
                                              @Param("endDate") OffsetDateTime endDate,
                                              Pageable pageable);
}
