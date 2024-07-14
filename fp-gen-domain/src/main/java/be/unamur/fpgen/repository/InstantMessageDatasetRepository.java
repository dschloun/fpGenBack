package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.InstantMessageDataset;

import java.util.UUID;

public interface InstantMessageDatasetRepository {

    InstantMessageDataset saveInstantMessageDataset(InstantMessageDataset instantMessageDataset);

    InstantMessageDataset getInstantMessageDatasetById(UUID instantMessageDatasetId);

    default void deleteInstantMessageDatasetById(UUID instantMessageDatasetId) {

    }
}
