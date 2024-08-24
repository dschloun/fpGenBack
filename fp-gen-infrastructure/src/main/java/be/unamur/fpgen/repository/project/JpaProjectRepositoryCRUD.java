package be.unamur.fpgen.repository.project;

import be.unamur.fpgen.entity.project.ProjectEntity;
import be.unamur.fpgen.project.ProjectTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaProjectRepositoryCRUD extends JpaRepository<ProjectEntity, UUID> {
    List<ProjectEntity> findByAuthorId(UUID authorId);

    @Query(value = "SELECT DISTINCT p from ProjectEntity p" +
            " WHERE p.type = :type" +
            " AND (:name is null or lower(p.name) like %:name%)" +
            " AND (:description is null or lower(p.description) like %:description%)" +
            " AND (:organization is null or lower(p.organisation) like %:organization%)" +
            " AND (:authorTrigram is null or lower(p.author.trigram) like %:authorTrigram%)" +
            " AND p.creationDate >= cast(:startDate as timestamp)" +
            " AND p.creationDate <= cast(:endDate as timestamp)"
    )
    Page<ProjectEntity> findPagination(@Param("type") ProjectTypeEnum type,
                                       @Param("name") String name,
                                       @Param("description") String description,
                                       @Param("organization") String organization,
                                       @Param("authorTrigram") String authorTrigram,
                                       @Param("startDate") OffsetDateTime startDate,
                                       @Param("endDate") OffsetDateTime endDate,
                                       Pageable pageable);
}
