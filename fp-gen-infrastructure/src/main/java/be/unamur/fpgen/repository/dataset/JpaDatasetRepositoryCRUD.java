package be.unamur.fpgen.repository.dataset;

import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaDatasetRepositoryCRUD extends JpaRepository<DatasetEntity, UUID> {
    String COMMON = " WHERE (:authorTrigram is null or lower(d.author.trigram) like %:authorTrigram%) " +
            "AND (:name is null or lower(d.name) like %:name%) " +
            "AND (:version is null or d.version = :version) " +
            "AND (:description is null or lower(d.description) like %:description%) " +
            "AND (:comment is null or lower(d.comment) like %:comment%) " +
            "AND d.creationDate >= cast(:startDate as timestamp) " +
            "AND d.creationDate <= cast(:endDate as timestamp) ";

    @Query(value = "SELECT DISTINCT d from InstantMessageDatasetEntity d " +
            COMMON)
    Page<InstantMessageDatasetEntity> findInstantMessagePagination(
            @Param("name") String name,
            @Param("version") Integer version,
            @Param("authorTrigram") String authorTrigram,
            @Param("description") String description,
            @Param("comment") String comment,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            Pageable pageable);

    @Query(value = "SELECT DISTINCT d from ConversationDatasetEntity d " +
            COMMON)
    Page<ConversationDatasetEntity> findConversationPagination(
            @Param("name") String name,
            @Param("version") Integer version,
            @Param("authorTrigram") String authorTrigram,
            @Param("description") String description,
            @Param("comment") String comment,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            Pageable pageable);

    List<DatasetEntity> findAllByIdOrOriginalDatasetIdOrderByVersionDesc(UUID id, UUID originalDatasetId);

    Optional<DatasetEntity> findByOriginalDatasetIdAndVersion(UUID originalDatasetId, Integer version);
}
