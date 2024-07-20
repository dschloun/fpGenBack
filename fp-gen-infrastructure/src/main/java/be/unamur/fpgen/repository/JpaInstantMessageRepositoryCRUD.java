package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface JpaInstantMessageRepositoryCRUD extends JpaRepository<InstantMessageEntity, UUID> {

    @Query(value = "SELECT DISTINCT i from instant_message i" +
//            " WHERE kind = 'SIM'" +
            " WHERE (:topic is null or i.topic = :topic)" +
            " AND (:type is null or i.type = :type)" +
            " AND (:content is null or lower(i.content) like %:content%)" +
            " AND (:startDate is null or i.creationDate >= :startDate)" +
            " AND (:endDate is null or i.creationDate <= :endDate)"
    )
    Page<InstantMessageEntity> findPagination(@Param("topic") MessageTopicEnum topic,
                                              @Param("type") MessageTypeEnum type,
                                              @Param("content") String content,
                                              @Param("startDate") OffsetDateTime startDate,
                                              @Param("endDate") OffsetDateTime endDate,
                                              Pageable pageable);
}
