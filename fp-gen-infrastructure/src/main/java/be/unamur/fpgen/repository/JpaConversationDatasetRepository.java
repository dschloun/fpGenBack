package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaConversationDatasetRepository implements ConversationDatasetRepository{
    @Override
    public ConversationDataset saveConversationDataset(ConversationDataset conversationDataset) {
        return null;
    }

    @Override
    public ConversationDataset getConversationDatasetById(UUID conversationDatasetId) {
        return null;
    }

    @Override
    public void deleteConversationDatasetById(UUID conversationDatasetId) {

    }
}
