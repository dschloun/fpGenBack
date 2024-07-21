package be.unamur.fpgen.web;

import be.unamur.api.GenerationApi;
import be.unamur.fpgen.mapper.domainToWeb.pagination.GenerationPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.GenerationPaginationWebToDomainMapper;
import be.unamur.fpgen.service.ConversationGenerationService;
import be.unamur.fpgen.service.InstantMessageGenerationService;
import be.unamur.model.Generation;
import be.unamur.model.GenerationType;
import be.unamur.model.GenerationsPage;
import be.unamur.model.PagedGenerationQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Controller
public class GenerationController implements GenerationApi {
    private final InstantMessageGenerationService instantMessageGenerationService;
    private final ConversationGenerationService conversationGenerationService;
    public GenerationController(InstantMessageGenerationService instantMessageGenerationService, ConversationGenerationService conversationGenerationService) {
        this.instantMessageGenerationService = instantMessageGenerationService;
        this.conversationGenerationService = conversationGenerationService;
    }

    @Override
    public ResponseEntity<Generation> getGenerationById(UUID generationId) {
        return GenerationApi.super.getGenerationById(generationId);
    }

    @Override
    public ResponseEntity<GenerationsPage> searchGenerationsPaginate(@NotNull @Valid GenerationType generationType, @Valid PagedGenerationQuery pagedGenerationQuery) {
        GenerationsPage generationsPage;
        if (GenerationType.INSTANT_MESSAGE.equals(generationType)){
            generationsPage = GenerationPaginationDomainToWebMapper.map(
                    instantMessageGenerationService.searchGenerationsPaginate(GenerationPaginationWebToDomainMapper.map(
                            pagedGenerationQuery
                    ))
            );
        } else if (GenerationType.CONVERSATION.equals(generationType)){
            generationsPage = GenerationPaginationDomainToWebMapper.map(
                    conversationGenerationService.searchGenerationsPaginate(GenerationPaginationWebToDomainMapper.map(
                            pagedGenerationQuery
                    ))
            );
        } else {
            throw new IllegalArgumentException("Unsupported generation type");
        }
        return new ResponseEntity<>(generationsPage, HttpStatus.OK);
    }
}
