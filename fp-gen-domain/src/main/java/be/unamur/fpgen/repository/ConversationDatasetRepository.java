package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;

import java.util.UUID;

public interface ConversationDatasetRepository {

    ConversationDataset saveConversationDataset(ConversationDataset conversationDataset);

    ConversationDataset getConversationDatasetById(UUID conversationDatasetId);

    void deleteConversationDatasetById(UUID conversationDatasetId);
}
