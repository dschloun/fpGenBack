package be.unamur.fpgen.repository;

import be.unamur.fpgen.conversation.Conversation;

import java.util.UUID;

public class JpaConversationRepository implements ConversationRepository{
    @Override
    public Conversation saveConversation(Conversation conversation) {
        return null;
    }

    @Override
    public Conversation getConversationById(UUID conversationId) {
        return null;
    }

    @Override
    public void deleteConversationById(UUID conversationId) {

    }
}
