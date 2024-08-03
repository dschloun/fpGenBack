package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.dataset.DatasetFunctionEnum;
import be.unamur.fpgen.mapper.webToDomain.ProjectWebToDomainMapper;
import be.unamur.fpgen.project.Project;
import be.unamur.fpgen.repository.ProjectRepository;
import be.unamur.model.ProjectCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ProjectService {
    private final AuthorService authorService;
    private final ProjectRepository projectRepository;

    public ProjectService(AuthorService authorService, ProjectRepository projectRepository) {
        this.authorService = authorService;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Project createProject(ProjectCreation projectCreation){
        // 0. get author
        final Author author = authorService.getAuthorById(projectCreation.getAuthorId());

        // 1. generate 3 datasets for the project
        final Project project = ProjectWebToDomainMapper.map(projectCreation, author);
        project.generateInitialDatasets();

        return projectRepository.save(project, author);
    }


}
