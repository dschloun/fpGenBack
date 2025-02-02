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

/**
 * This rest controller class is the implementation of the ConversationApi interface.
 * It is used to handle the requests related to the conversations.
 */
@Controller
public class ConversationController implements ConversationApi {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    /**
     * This method is used to create a new conversation.
     * @param conversationBatchCreation the conversation to create
     * @return the created conversation
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> generateConversations(@Valid ConversationBatchCreation conversationBatchCreation) {
        conversationService.generateConversationList(conversationBatchCreation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * This method is used to get a conversation by its id.
     * @param conversationId the id of the conversation
     * @return the conversation
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Conversation> getConversationById(UUID conversationId) {
        return new ResponseEntity<>(
                ConversationDomainToWebMapper
                        .map(conversationService.getConversationById(conversationId))
                , HttpStatus.OK);
    }

    /**
     * This method is used to get the list of messages of a conversation.
     * @param conversationId the id of the conversation
     * @return the list of messages
     */
    //todo not very useful, could be removed
    @Override
    public ResponseEntity<List<ConversationMessage>> getConversationInstantMessageListById(UUID conversationId, UUID conversationInstantMessageId) {
        return ConversationApi.super.getConversationInstantMessageListById(conversationId, conversationInstantMessageId);
    }

    /**
     * search conversations by a query
     * @param pagedConversationQuery the query
     * @return the paginated list of conversations
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<ConversationsPage> searchConversationsPaginate(@Valid PagedConversationQuery pagedConversationQuery) {
        return new ResponseEntity<>(ConversationPaginationDomainToWebMapper.map(
                conversationService.searchConversationsPaginate(
                        ConversationPaginationWebToDomainMapper.map(pagedConversationQuery)))
                , HttpStatus.OK);
    }

    /**
     * This method is used to delete a conversation by its id.
     * @param conversationId the id of the conversation
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> deleteConversationById(UUID conversationId) {
        conversationService.deleteConversationById(conversationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * This method is used to get the list of conversations of a generation.
     * @param generationId the id of the generation
     * @return the list of conversations
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<Conversation>> findConversationByGenerationId(UUID generationId) {
        return new ResponseEntity<>(MapperUtil.mapList(conversationService.findAllByGenerationId(generationId), ConversationDomainToWebMapper::map), HttpStatus.OK);
    }
}
