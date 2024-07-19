package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.instant_message.InstantMessage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface InstantMessageDatasetRepository {

    InstantMessageDataset saveInstantMessageDataset(InstantMessageDataset instantMessageDataset);

    Optional<InstantMessageDataset> findInstantMessageDatasetById(UUID instantMessageDatasetId);

    default void deleteInstantMessageDatasetById(UUID instantMessageDatasetId) {    }

    void addInstantMessageListToDataset(InstantMessageDataset dataset, Set<InstantMessageGeneration> generations);
}
