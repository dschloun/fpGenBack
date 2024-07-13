package be.unamur.fpgen.web;

import be.unamur.api.InstantMessageApi;
import be.unamur.fpgen.service.InstantMessageBatchGenerationService;
import be.unamur.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
public class InstantMessageController implements InstantMessageApi {

    private final InstantMessageBatchGenerationService instantMessageBatchGenerationService;

    public InstantMessageController(final InstantMessageBatchGenerationService instantMessageBatchGenerationService) {
        this.instantMessageBatchGenerationService = instantMessageBatchGenerationService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return InstantMessageApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> createMessage(@Valid InstantMessageBatchCreation instantMessageBatchCreation) {
        System.out.println("test creation message");
        instantMessageBatchGenerationService.generateInstantMessages(instantMessageBatchCreation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<InstantMessage> getInstantMessageById(UUID instantMessageId) {
        System.out.println("test");
        return InstantMessageApi.super.getInstantMessageById(instantMessageId);
    }

}
