package be.unamur.fpgen.web;

import be.unamur.api.GenerationApi;
import be.unamur.fpgen.mapper.domainToWeb.pagination.GenerationPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.GenerationPaginationWebToDomainMapper;
import be.unamur.fpgen.service.GenerationService;
import be.unamur.model.Generation;
import be.unamur.model.GenerationsPage;
import be.unamur.model.PagedGenerationQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class GenerationController implements GenerationApi {
    private final GenerationService generationService;

    public GenerationController(GenerationService generationService) {
        this.generationService = generationService;
    }

    @Override
    public ResponseEntity<Generation> getGenerationById(UUID generationId) {
        return GenerationApi.super.getGenerationById(generationId);
    }

    @Override
    public ResponseEntity<GenerationsPage> searchGenerationsPaginate(@Valid PagedGenerationQuery pagedGenerationQuery) {
        return new ResponseEntity<>(GenerationPaginationDomainToWebMapper.map(
                generationService.searchGenerationsPaginate(GenerationPaginationWebToDomainMapper.map(
                        pagedGenerationQuery
                ))
        ), HttpStatus.OK);
    }
}
