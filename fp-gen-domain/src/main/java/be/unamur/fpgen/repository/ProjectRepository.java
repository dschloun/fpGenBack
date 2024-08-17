package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.project.Project;
import be.unamur.fpgen.project.pagination.ProjectsPage;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository {
    Project save(Project project, Author author);

    Project update(Project project);

    Optional<Project> findById(UUID id);

    List<Project> findAllByAuthorId(UUID authorId);

    void deleteById(UUID id);

    ProjectsPage findPagination(String name, String description, String organization, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);
}
