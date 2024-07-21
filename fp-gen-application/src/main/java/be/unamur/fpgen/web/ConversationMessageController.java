package be.unamur.fpgen.web;

import be.unamur.api.ConversationMessageApi;
import be.unamur.model.ConversationMessagesPage;
import be.unamur.model.PagedConversationMessageQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@Controller
public class ConversationMessageController implements ConversationMessageApi {

    @Override
    public ResponseEntity<ConversationMessagesPage> searchConversationMessagesPaginate(@Valid PagedConversationMessageQuery pagedConversationMessageQuery) {
        return ConversationMessageApi.super.searchConversationMessagesPaginate(pagedConversationMessageQuery);
    }
}
