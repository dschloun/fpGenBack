package be.unamur.fpgen.web;

import be.unamur.api.ConversationApi;
import be.unamur.model.Conversation;
import be.unamur.model.ConversationBatchCreation;
import be.unamur.model.ConversationInstantMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ConversationController implements ConversationApi {
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ConversationApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> generateConversations(@Valid ConversationBatchCreation conversationBatchCreation) {
        return ConversationApi.super.generateConversations(conversationBatchCreation);
    }

    @Override
    public ResponseEntity<Conversation> getConversationById(UUID conversationId) {
        return ConversationApi.super.getConversationById(conversationId);
    }

    @Override
    public ResponseEntity<List<ConversationInstantMessage>> getConversationInstantMessageListById(UUID conversationId, UUID conversationInstantMessageId) {
        return ConversationApi.super.getConversationInstantMessageListById(conversationId, conversationInstantMessageId);
    }
}
