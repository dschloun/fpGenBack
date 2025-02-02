package be.unamur.fpgen.web;

import be.unamur.api.GenerationApi;
import be.unamur.fpgen.mapper.domainToWeb.pagination.GenerationPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.GenerationPaginationWebToDomainMapper;
import be.unamur.fpgen.service.GenerationService;
import be.unamur.fpgen.service.identification.AuthorVerification;
import be.unamur.model.GenerationsPage;
import be.unamur.model.PagedGenerationQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.UUID;

/**
 * This rest controller class is the implementation of the GenerationApi interface.
 * It is used to manage the generations of the application.
 */
@Controller
public class GenerationController implements GenerationApi {
    private final GenerationService generationService;
    private final AuthorVerification authorVerification;

    public GenerationController(GenerationService generationService) {
        this.generationService = generationService;
        this.authorVerification = AuthorVerification.newBuilder().withFindByIdService(generationService).build();
    }

    /**
     * This method is used to search generations with pagination.
     * It is only accessible to users.
     *
     * @param pagedGenerationQuery the query to search generations
     * @return the generations page
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<GenerationsPage> searchGenerationsPaginate(@Valid PagedGenerationQuery pagedGenerationQuery) {
        GenerationsPage generationsPage = GenerationPaginationDomainToWebMapper.map(
                generationService.searchGenerationsPaginate(GenerationPaginationWebToDomainMapper.map(
                        pagedGenerationQuery
                ))
        );
        return new ResponseEntity<>(generationsPage, HttpStatus.OK);
    }

    /**
     * This method is used to delete a generation by its id.
     * It is only accessible to administrators.
     * @param generationId the id of the generation to delete
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> deleteGenerationById(UUID generationId) {
        authorVerification.verifyAuthor(generationId);
        generationService.deleteGenerationById(generationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
