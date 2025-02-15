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

/**
 * This rest controller class is the implementation of the ProjectApi interface.
 * It is used to manage the projects of the application.
 */
@Controller
public class ProjectController implements ProjectApi {
    private final ProjectService projectService;
    private final AuthorVerification authorVerification;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
        this.authorVerification = AuthorVerification.newBuilder().withFindByIdService(projectService).build();
    }

    /**
     * This method is used to create a new project.
     * @param projectCreation the project creation data
     * @return the created project
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Project> createProject(@Valid ProjectCreation projectCreation) {
        return new ResponseEntity<>(ProjectDomainToWebMapper.map(
                projectService.createProject(projectCreation)
        ), HttpStatus.OK);
    }

    /**
     * This method is used to delete a project.
     * @param projectId the id of the project to delete
     * @return a response entity with no content
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> deleteProject(UUID projectId) {
        authorVerification.verifyAuthor(projectId);
        projectService.deleteById(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * This method is used to get a project by its id.
     * @param projectId the id of the project to get
     * @return the project
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Project> getProjectById(UUID projectId) {
        return new ResponseEntity<>(ProjectDomainToWebMapper.map(
                projectService.findById(projectId)
        ), HttpStatus.OK);
    }

    /**
     * search projects with pagination
     * @param pagedProjectQuery
     * @return a page of projects
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<ProjectsPage> searchProjectsPaginate(@Valid PagedProjectQuery pagedProjectQuery) {
        return new ResponseEntity<>(ProjectPaginationDomainToWebMapper.map(
                projectService.searchProjectPaginate(
                        ProjectPaginationWebToDomainMapper.map(
                                pagedProjectQuery))
        ), HttpStatus.OK);
    }

    /**
     * This method is used to update the data of a project.
     * @param projectId the id of the project to update
     * @param UUID the list of UUIDs to update
     * @return the updated project
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Project> updateProjectData(UUID projectId, @Valid List<UUID> UUID) {
        return ProjectApi.super.updateProjectData(projectId, UUID);
    }

    /**
     * This method is used to get the training and test differences of a project.
     * @param projectId the id of the project
     * @return the training and test differences
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<TrainingTestDifferences> getDatasetsTrainingTestDifferencesByProjectId(UUID projectId) {
        return new ResponseEntity<>(TrainingTestDifferenceDomainToWebMapper.map(projectService.computeTrainingTestDifference(projectId)), HttpStatus.OK);
    }
}
