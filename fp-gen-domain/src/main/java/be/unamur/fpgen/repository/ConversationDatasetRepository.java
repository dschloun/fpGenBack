package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.generation.ConversationGeneration;

import java.util.Set;
import java.util.UUID;

public interface ConversationDatasetRepository {

    ConversationDataset saveConversationDataset(ConversationDataset conversationDataset);

    ConversationDataset getConversationDatasetById(UUID conversationDatasetId);

    void deleteConversationDatasetById(UUID conversationDatasetId);

    void addConversationListToDataset(ConversationDataset dataset, Set<ConversationGeneration> generations);
}
