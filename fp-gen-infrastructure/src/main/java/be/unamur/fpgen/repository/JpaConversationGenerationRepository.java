package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ConversationGeneration;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaConversationGenerationRepository implements ConversationGenerationRepository{
    @Override
    public ConversationGeneration saveConversationGeneration(ConversationGeneration conversationGeneration) {
        return null;
    }

    @Override
    public ConversationGeneration getConversationGenerationById(UUID conversationGenerationId) {
        return null;
    }

    @Override
    public void deleteConversationGenerationById(UUID conversationGenerationId) {

    }
}
