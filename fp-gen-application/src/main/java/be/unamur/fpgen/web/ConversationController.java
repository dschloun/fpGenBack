package be.unamur.fpgen.web;

import be.unamur.api.ConversationApi;
import be.unamur.fpgen.mapper.domainToWeb.ConversationDomainToWebMapper;
import be.unamur.fpgen.service.ConversationService;
import be.unamur.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ConversationController implements ConversationApi {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ConversationApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> generateConversations(@Valid ConversationBatchCreation conversationBatchCreation) {
        conversationService.createConversationList(conversationBatchCreation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Conversation> getConversationById(UUID conversationId) {
        return new ResponseEntity<>(
                ConversationDomainToWebMapper
                        .map(conversationService.getConversationById(conversationId))
                , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ConversationMessage>> getConversationInstantMessageListById(UUID conversationId, UUID conversationInstantMessageId) {
        return ConversationApi.super.getConversationInstantMessageListById(conversationId, conversationInstantMessageId);
    }

    @Override
    public ResponseEntity<ConversationsPage> searchConversationsPaginate(@Valid PagedConversationQuery pagedConversationQuery) {
        return ConversationApi.super.searchConversationsPaginate(pagedConversationQuery);
    }
}
