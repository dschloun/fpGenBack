package be.unamur.fpgen.web;

import be.unamur.api.AdministrationApi;
import be.unamur.model.MessageType;
import be.unamur.model.Prompt;
import be.unamur.model.PromptCreation;
import be.unamur.model.PromptUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class Administration implements AdministrationApi {
    @Override
    public ResponseEntity<List<Prompt>> createPrompt(@Valid PromptCreation promptCreation) {
        return AdministrationApi.super.createPrompt(promptCreation);
    }

    @Override
    public ResponseEntity<Prompt> getPromptById(UUID promptId) {
        return AdministrationApi.super.getPromptById(promptId);
    }

    @Override
    public ResponseEntity<List<Prompt>> getPromptsByCategories(@Valid List<MessageType> messageType) {
        return AdministrationApi.super.getPromptsByCategories(messageType);
    }

    @Override
    public ResponseEntity<Void> selectDefaultPrompt(UUID promptId) {
        return AdministrationApi.super.selectDefaultPrompt(promptId);
    }

    @Override
    public ResponseEntity<Void> updateAuthorStatus(UUID authorId, @Valid String body) {
        return AdministrationApi.super.updateAuthorStatus(authorId, body);
    }

    @Override
    public ResponseEntity<Prompt> updatePromptById(UUID promptId, @Valid PromptUpdate promptUpdate) {
        return AdministrationApi.super.updatePromptById(promptId, promptUpdate);
    }

    @Override
    public ResponseEntity<Void> validatePrompt(UUID promptId) {
        return AdministrationApi.super.validatePrompt(promptId);
    }
}
