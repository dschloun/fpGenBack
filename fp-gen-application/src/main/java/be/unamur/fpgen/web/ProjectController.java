package be.unamur.fpgen.web;

import be.unamur.api.ProjectApi;
import be.unamur.fpgen.mapper.domainToWeb.ProjectDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.TrainingTestDifferenceDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.pagination.ProjectPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.ProjectWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.ProjectPaginationWebToDomainMapper;
import be.unamur.fpgen.service.ProjectService;
import be.unamur.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Controller
public class ProjectController implements ProjectApi {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ResponseEntity<Project> createProject(@Valid ProjectCreation projectCreation) {
        return new ResponseEntity<>(ProjectDomainToWebMapper.map(
                projectService.createProject(projectCreation)
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteProject(UUID projectId) {
        projectService.deleteById(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Project> getProjectById(UUID projectId) {
        return new ResponseEntity<>(ProjectDomainToWebMapper.map(
                projectService.findById(projectId)
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectsPage> searchProjectsPaginate(@Valid PagedProjectQuery pagedProjectQuery) {
        return new ResponseEntity<>(ProjectPaginationDomainToWebMapper.map(
                projectService.searchProjectPaginate(
                        ProjectPaginationWebToDomainMapper.map(
                                pagedProjectQuery))
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Project> updateProjectData(UUID projectId, @Valid List<UUID> UUID) {
        return ProjectApi.super.updateProjectData(projectId, UUID);
    }

    @Override
    public ResponseEntity<TrainingTestDifferences> getDatasetsTrainingTestDifferencesByProjectId(UUID projectId) {
        return new ResponseEntity<>(TrainingTestDifferenceDomainToWebMapper.map(projectService.computeTrainingTestDifference(projectId)), HttpStatus.OK);
    }
}
