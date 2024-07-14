package be.unamur.fpgen.repository;

import be.unamur.fpgen.conversation.Conversation;

import java.util.UUID;

public interface ConversationRepository {

    Conversation saveConversation(Conversation conversation);

    Conversation getConversationById(UUID conversationId);

    void deleteConversationById(UUID conversationId);
}
