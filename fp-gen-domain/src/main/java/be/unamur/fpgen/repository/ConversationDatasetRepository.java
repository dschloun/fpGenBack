package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.generation.ConversationGeneration;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public interface ConversationDatasetRepository {

    ConversationDataset saveConversationDataset(ConversationDataset conversationDataset);

    ConversationDataset getConversationDatasetById(UUID conversationDatasetId);

    void deleteConversationDatasetById(UUID conversationDatasetId);

    void addConversationListToDataset(ConversationDataset dataset, Set<ConversationGeneration> generations);

    DatasetsPage findPagination(String version, String name, String description, String comment, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);
}
