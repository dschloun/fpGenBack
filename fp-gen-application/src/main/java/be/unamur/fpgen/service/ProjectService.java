package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.exception.ProjectNotFoundException;
import be.unamur.fpgen.mapper.webToDomain.ProjectWebToDomainMapper;
import be.unamur.fpgen.project.Project;
import be.unamur.fpgen.project.pagination.PagedProjectsQuery;
import be.unamur.fpgen.project.pagination.ProjectsPage;
import be.unamur.fpgen.repository.ProjectRepository;
import be.unamur.model.ProjectCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        project.generateInitialDatasets(getDatasetType(projectCreation));

        return projectRepository.save(project, author);
    }

    @Transactional
    public Project findById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> ProjectNotFoundException.withId(projectId));
    }

    @Transactional
    public ProjectsPage searchProjectPaginate(final PagedProjectsQuery query){
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        return projectRepository.findPagination(
                query.getProjectQuery().getType(),
                query.getProjectQuery().getName(),
                query.getProjectQuery().getDescription(),
                query.getProjectQuery().getOrganization(),
                query.getProjectQuery().getAuthorTrigram(),
                query.getProjectQuery().getStartDate(),
                query.getProjectQuery().getEndDate(),
                pageable);
    }

    @Transactional
    public void deleteById(UUID projectId){
        projectRepository.deleteById(projectId);
    }

    private DatasetTypeEnum getDatasetType(final ProjectCreation projectCreation){
        if (projectCreation.getProjectType().name().equals(DatasetTypeEnum.INSTANT_MESSAGE.name())){
            return DatasetTypeEnum.INSTANT_MESSAGE;
        } else {
            return DatasetTypeEnum.CONVERSATION;
        }
    }
}