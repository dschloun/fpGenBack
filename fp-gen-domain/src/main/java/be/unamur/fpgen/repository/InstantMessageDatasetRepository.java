package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface InstantMessageDatasetRepository {

    InstantMessageDataset saveInstantMessageDataset(InstantMessageDataset instantMessageDataset);

    InstantMessageDataset saveNewVersion(InstantMessageDataset oldVersion, InstantMessageDataset newVersion);

    InstantMessageDataset updateInstantMessageDataset(InstantMessageDataset instantMessageDataset);

    Optional<InstantMessageDataset> findInstantMessageDatasetById(UUID instantMessageDatasetId);

    default void deleteInstantMessageDatasetById(UUID instantMessageDatasetId) {    }

    void addInstantMessageListToDataset(InstantMessageDataset dataset, Set<InstantMessageGeneration> generations);

    void removeInstantMessageListFromDataset(InstantMessageDataset dataset, Set<InstantMessageGeneration> generations);
    DatasetsPage findPagination(String version, String name, String description, String comment, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);

    void addOngoingGenerationToDataset(InstantMessageDataset dataset, OngoingGeneration generation);

    void removeOngoingGenerationToDataset(InstantMessageDataset dataset, OngoingGeneration generation);
}
