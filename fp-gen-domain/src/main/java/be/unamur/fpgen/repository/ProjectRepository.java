package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.project.Project;
import be.unamur.fpgen.project.ProjectTypeEnum;
import be.unamur.fpgen.project.pagination.ProjectsPage;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for project.
 */
public interface ProjectRepository {

    /**
     * Save the project.
     * @param project
     * @param author
     * @return the saved project
     */
    Project save(Project project, Author author);

    /**
     * Update the project.
     * @param project
     * @return the updated project
     */
    Project update(Project project);

    /**
     * Find the project by its id.
     * @param id the id of the project
     * @return the project empty if not found
     */
    Optional<Project> findById(UUID id);

    /**
     * Find all projects by author id.
     * @param authorId the id of the author
     * @return the list of projects
     */
    List<Project> findAllByAuthorId(UUID authorId);

    /**
     * Delete the project by its id.
     * @param id the id of the project
     */
    void deleteById(UUID id);

    /**
     * Find all projects which match the given filters.
     * @param projectType
     * @param name
     * @param description
     * @param organization
     * @param authorTrigram
     * @param startDate
     * @param endDate
     * @param pageable
     * @return a pageable of projects
     */
    ProjectsPage findPagination(ProjectTypeEnum projectType, String name, String description, String organization, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);
}
