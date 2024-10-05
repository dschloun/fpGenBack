package be.unamur.fpgen.web;

import be.unamur.api.ConversationApi;
import be.unamur.fpgen.mapper.domainToWeb.ConversationDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.pagination.ConversationPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.ConversationPaginationWebToDomainMapper;
import be.unamur.fpgen.service.ConversationService;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class ConversationController implements ConversationApi {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> generateConversations(@Valid ConversationBatchCreation conversationBatchCreation) {
        conversationService.generateConversationList(conversationBatchCreation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Conversation> getConversationById(UUID conversationId) {
        return new ResponseEntity<>(
                ConversationDomainToWebMapper
                        .map(conversationService.getConversationById(conversationId))
                , HttpStatus.OK);
    }

    //fixme not very useful
    @Override
    public ResponseEntity<List<ConversationMessage>> getConversationInstantMessageListById(UUID conversationId, UUID conversationInstantMessageId) {
        return ConversationApi.super.getConversationInstantMessageListById(conversationId, conversationInstantMessageId);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<ConversationsPage> searchConversationsPaginate(@Valid PagedConversationQuery pagedConversationQuery) {
        return new ResponseEntity<>(ConversationPaginationDomainToWebMapper.map(
                conversationService.searchConversationsPaginate(
                        ConversationPaginationWebToDomainMapper.map(pagedConversationQuery)))
                , HttpStatus.OK);
    }

    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> deleteConversationById(UUID conversationId) {
        conversationService.deleteConversationById(conversationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<Conversation>> findConversationByGenerationId(UUID generationId) {
        return new ResponseEntity<>(MapperUtil.mapList(conversationService.findAllByGenerationId(generationId), ConversationDomainToWebMapper::map), HttpStatus.OK);
    }
}
