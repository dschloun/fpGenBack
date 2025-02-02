package be.unamur.fpgen.web;

import be.unamur.api.AdministrationApi;
import be.unamur.fpgen.mapper.domainToWeb.PromptDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.AuthorWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.DatasetTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.PromptWebToDomainMapper;
import be.unamur.fpgen.service.AuthorService;
import be.unamur.fpgen.service.PromptService;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * This rest controller class is the implementation of the AdministrationApi interface.
 * It is used to manage the prompts and authors.
 */
@Controller
public class AdministrationController implements AdministrationApi {
    private final PromptService promptService;
    private final AuthorService authorService;

    public AdministrationController(PromptService promptService, AuthorService authorService) {
        this.promptService = promptService;
        this.authorService = authorService;
    }

    /**
     * This method is used to create a prompt.
     * @param promptCreation the prompt to create
     * @return the created prompt
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Prompt> createPrompt(@Valid PromptCreation promptCreation) {
        return new ResponseEntity<>(PromptDomainToWebMapper.map(promptService.create(promptCreation)),
                HttpStatus.CREATED);
    }

    /**
     * This method is used to get a prompt by its id.
     * @param promptId
     * @return the prompt
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Prompt> getPromptById(UUID promptId) {
        return new ResponseEntity<>(PromptDomainToWebMapper.map(promptService.findById(promptId)),
                HttpStatus.OK);
    }

    /**
     * This method is used to get all the prompts.
     * @return the list of prompts
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<Prompt>> getPromptsByDatasetTypeAndMessageType(@NotNull @Valid DatasetType datasetType, @NotNull @Valid MessageType messageType) {
        return new ResponseEntity<>(MapperUtil.mapList(promptService.findAllPromptsByDatasetTypeAndMessageType(DatasetTypeWebToDomainMapper.map(datasetType), MessageTypeWebToDomainMapper.map(messageType)),
                PromptDomainToWebMapper::map), HttpStatus.OK);
    }

    /**
     * This method is used to get all the prompts by their status.
     * @param promptStatus
     * @return the list of prompts
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<Prompt>> getPromptsByStatus(@NotNull @Valid PromptStatusEnum promptStatus) {
        return new ResponseEntity<>(MapperUtil.mapList(promptService.findAllPromptsByStatus(PromptWebToDomainMapper.map(promptStatus)),
                PromptDomainToWebMapper::map), HttpStatus.OK);
    }

    /**
     * This method is used to set a prompt as the default prompt.
     * @param promptId
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> selectDefaultPrompt(UUID promptId) {
        promptService.setDefaultPrompt(promptId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * update the status of an author
     * @param authorId
     * @param authorStatus
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> updateAuthorStatus(UUID authorId, @NotNull @Valid AuthorStatusEnum authorStatus) {
        authorService.updateAuthorStatus(authorId, AuthorWebToDomainMapper.map(authorStatus));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * update a prompt
     * @param promptId
     * @param promptUpdate
     * @return the updated prompt
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Prompt> updatePromptById(UUID promptId, @Valid PromptUpdate promptUpdate) {
        return new ResponseEntity<>(PromptDomainToWebMapper.map(promptService.updatePrompt(promptId, promptUpdate)),HttpStatus.OK);
    }

    /**
     * update the status of a prompt
     * @param promptId
     * @param promptStatus
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> updatePromptStatus(UUID promptId, @NotNull @Valid PromptStatusEnum promptStatus) {
        promptService.updatePromptStatus(promptId, PromptWebToDomainMapper.map(promptStatus));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
