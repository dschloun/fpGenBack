package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.generation.ConversationGeneration;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ConversationGenerationRepository {

    ConversationGeneration saveConversationGeneration(ConversationGeneration conversationGeneration);

    Optional<ConversationGeneration> findConversationGenerationById(UUID conversationGenerationId);

    void deleteConversationGenerationById(UUID conversationGenerationId);

}
