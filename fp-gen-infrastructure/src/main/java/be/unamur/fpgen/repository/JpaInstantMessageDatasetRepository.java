package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.InstantMessageDataset;

import java.util.UUID;

public class JpaInstantMessageDatasetRepository implements InstantMessageDatasetRepository{
    @Override
    public InstantMessageDataset saveInstantMessageDataset(InstantMessageDataset instantMessageDataset) {
        return null;
    }

    @Override
    public InstantMessageDataset getInstantMessageDatasetById(UUID instantMessageDatasetId) {
        return null;
    }

    @Override
    public void deleteInstantMessageDatasetById(UUID instantMessageDatasetId) {
        InstantMessageDatasetRepository.super.deleteInstantMessageDatasetById(instantMessageDatasetId);
    }
}
