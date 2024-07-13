package be.unamur.fpgen.web;

import be.unamur.api.V1Api;
import be.unamur.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
public class InstantMessageController implements V1Api{
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return V1Api.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> createMessage(@Valid InstantMessageBatchCreation instantMessageBatchCreation) {
        return V1Api.super.createMessage(instantMessageBatchCreation);
    }

    @Override
    public ResponseEntity<Void> generateConversations(@Valid ConversationBatchCreation conversationBatchCreation) {
        return V1Api.super.generateConversations(conversationBatchCreation);
    }

    @Override
    public ResponseEntity<Conversation> getConversationById(UUID conversationId) {
        return V1Api.super.getConversationById(conversationId);
    }

    @Override
    public ResponseEntity<ConversationInstantMessage> getConversationInstantMessageById(UUID conversationId, UUID conversationInstantMessageId) {
        return V1Api.super.getConversationInstantMessageById(conversationId, conversationInstantMessageId);
    }

    @Override
    public ResponseEntity<InstantMessage> getInstantMessageById(UUID instantMessageId) {
        System.out.println("test");
        return V1Api.super.getInstantMessageById(instantMessageId);
    }
}
