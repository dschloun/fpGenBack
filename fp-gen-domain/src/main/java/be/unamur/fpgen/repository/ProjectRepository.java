package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.project.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository {
    Project save(Project project, Author author);

    Project update(Project project);

    Optional<Project> findById(UUID id);

    List<Project> findAllByAuthorId(UUID authorId);

    void deleteById(UUID id);
}
