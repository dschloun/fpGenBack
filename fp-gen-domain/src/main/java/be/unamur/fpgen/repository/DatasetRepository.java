package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface DatasetRepository {

    Dataset saveDataset(Dataset instantMessageDataset);

    Dataset saveNewVersion(Dataset oldVersion, Dataset newVersion);

    Dataset updateDataset(Dataset dataset);

    Optional<Dataset> findInstantMessageDatasetById(UUID dataset);

    List<Dataset> findAllDatasetVersions(UUID origineDatasetId);

    void deleteDatasetById(UUID datasetId);

    void addItemListToDataset(Dataset dataset, Set<Generation> generations);

    void removeItemListFromDataset(Dataset dataset, Set<Generation> generations);

    DatasetsPage findPagination(DatasetTypeEnum type, Integer version, String name, String description, String comment, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);

    void addOngoingGenerationToDataset(Dataset dataset, OngoingGeneration generation);

    void removeOngoingGenerationToDataset(Dataset dataset, OngoingGeneration generation);
}
