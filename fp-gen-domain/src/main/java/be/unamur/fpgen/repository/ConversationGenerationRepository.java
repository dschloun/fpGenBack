package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ConversationGeneration;

import java.util.UUID;

public interface ConversationGenerationRepository {

    ConversationGeneration saveConversationGeneration(ConversationGeneration conversationGeneration);

    ConversationGeneration getConversationGenerationById(UUID conversationGenerationId);

    void deleteConversationGenerationById(UUID conversationGenerationId);
}
