package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.project.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaProjectRepositoryCRUD extends JpaRepository<ProjectEntity, UUID> {
    List<ProjectEntity> findByAuthorId(UUID authorId);

    Page<ProjectEntity> findPagination(@Param("name") String name,
                                       @Param("description") String description,
                                       @Param("organization") String organization,
                                       @Param("authorTrigram") String authorTrigram,
                                       @Param("startDate") OffsetDateTime startDate,
                                       @Param("endDate") OffsetDateTime endDate,
                                       Pageable pageable);
}
