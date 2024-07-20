package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface JpaInstantMessageDatasetRepositoryCRUD extends JpaRepository<InstantMessageDatasetEntity, UUID> {
    @Query(value = "SELECT DISTINCT g from InstantMessageDatasetEntity g" +
            " WHERE (:authorTrigram is null or g.author.trigram = :authorTrigram)" +
            " AND (:name is null or lower(g.name) like %:name%)" +
            " AND (:version is null or lower(g.version) like %:version%)" +
            " AND (:description is null or lower(g.description) like %:description%)" +
            " AND (:comment is null or lower(g.comment) like %:comment%)" +
            " AND g.creationDate >= cast(:startDate as timestamp)" +
            " AND g.creationDate <= cast(:endDate as timestamp)"
    )
    Page<InstantMessageDatasetEntity> findPagination(@Param("name") String name,
                                                      @Param("version") String version,
                                                      @Param("authorTrigram") String authorTrigram,
                                                      @Param("description") String description,
                                                      @Param("comment") String comment,
                                                      @Param("startDate") OffsetDateTime startDate,
                                                      @Param("endDate") OffsetDateTime endDate,
                                                      Pageable pageable);
}
