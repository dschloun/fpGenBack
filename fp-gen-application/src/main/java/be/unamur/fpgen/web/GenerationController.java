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

@Controller
public class GenerationController implements GenerationApi {
    private final GenerationService generationService;
    private final AuthorVerification authorVerification;

    public GenerationController(GenerationService generationService) {
        this.generationService = generationService;
        this.authorVerification = AuthorVerification.newBuilder().withFindByIdService(generationService).build();
    }

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

    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> deleteGenerationById(UUID generationId) {
        authorVerification.verifyAuthor(generationId);
        generationService.deleteGenerationById(generationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
