package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.project.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaProjectRepositoryCRUD extends JpaRepository<ProjectEntity, UUID> {
    List<ProjectEntity> findByAuthorId(UUID authorId);
}
