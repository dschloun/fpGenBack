package be.unamur.fpgen.web;

import be.unamur.api.ProjectApi;
import be.unamur.fpgen.mapper.domainToWeb.ProjectDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.TrainingTestDifferenceDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.pagination.ProjectPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.ProjectPaginationWebToDomainMapper;
import be.unamur.fpgen.service.ProjectService;
import be.unamur.fpgen.service.identification.AuthorVerification;
import be.unamur.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class ProjectController implements ProjectApi {
    private final ProjectService projectService;
    private final AuthorVerification authorVerification;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
        this.authorVerification = AuthorVerification.newBuilder().withFindByIdService(projectService).build();
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Project> createProject(@Valid ProjectCreation projectCreation) {
        return new ResponseEntity<>(ProjectDomainToWebMapper.map(
                projectService.createProject(projectCreation)
        ), HttpStatus.OK);
    }

    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> deleteProject(UUID projectId) {
        authorVerification.verifyAuthor(projectId);
        projectService.deleteById(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Project> getProjectById(UUID projectId) {
        return new ResponseEntity<>(ProjectDomainToWebMapper.map(
                projectService.findById(projectId)
        ), HttpStatus.OK);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<ProjectsPage> searchProjectsPaginate(@Valid PagedProjectQuery pagedProjectQuery) {
        return new ResponseEntity<>(ProjectPaginationDomainToWebMapper.map(
                projectService.searchProjectPaginate(
                        ProjectPaginationWebToDomainMapper.map(
                                pagedProjectQuery))
        ), HttpStatus.OK);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Project> updateProjectData(UUID projectId, @Valid List<UUID> UUID) {
        return ProjectApi.super.updateProjectData(projectId, UUID);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<TrainingTestDifferences> getDatasetsTrainingTestDifferencesByProjectId(UUID projectId) {
        return new ResponseEntity<>(TrainingTestDifferenceDomainToWebMapper.map(projectService.computeTrainingTestDifference(projectId)), HttpStatus.OK);
    }
}
