package be.unamur.fpgen.web;

import be.unamur.api.ConversationMessageApi;
import be.unamur.fpgen.mapper.domainToWeb.pagination.ConversationMessagePaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.ConversationMessagePaginationWebToDomainMapper;
import be.unamur.fpgen.service.ConversationMessageService;
import be.unamur.model.ConversationMessagesPage;
import be.unamur.model.PagedConversationMessageQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

/**
 * This class is a rest controller that handles the conversation messages.
 */
@Controller
public class ConversationMessageController implements ConversationMessageApi {
    private final ConversationMessageService conversationMessageService;

    public ConversationMessageController(ConversationMessageService conversationMessageService) {
        this.conversationMessageService = conversationMessageService;
    }

    /**
     * This method is used to search conversation messages and return a paginated list of them.
     * @param pagedConversationMessageQuery the query to search the conversation messages.
     * @return a paginated list of conversation messages.
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<ConversationMessagesPage> searchConversationMessagesPaginate(@Valid PagedConversationMessageQuery pagedConversationMessageQuery) {
        return new ResponseEntity<>(ConversationMessagePaginationDomainToWebMapper.map(
                conversationMessageService.searchConversationMessagePaginate(
                        ConversationMessagePaginationWebToDomainMapper.map(
                        pagedConversationMessageQuery
                ))
        ), HttpStatus.OK);
    }
}
