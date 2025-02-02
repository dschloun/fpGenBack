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

/**
 * Repository for the Dataset entity.
 */
public interface DatasetRepository {

    /**
     * Save a new dataset.
     *
     * @param instantMessageDataset the dataset to save
     * @return the saved dataset
     */
    Dataset saveDataset(Dataset instantMessageDataset);

    /**
     * Save a new version of a dataset.
     *
     * @param oldVersion the old version of the dataset
     * @param newVersion the new version of the dataset
     * @return the new version of dataset
     */
    Dataset saveNewVersion(Dataset oldVersion, Dataset newVersion);

    /**
     * Update a dataset.
     *
     * @param dataset the dataset to update
     * @return the updated dataset
     */
    Dataset updateDataset(Dataset dataset);

    /**
     * Find a dataset by its id.
     * @param datasetId
     * @return the dataset or empty if not found
     */
    Optional<Dataset> findDatasetById(UUID datasetId);

    /**
     * Check if a dataset is linked to a Project
     * @param datasetId
     * @return a boolean (true if the dataset is linked to a project, false otherwise)
     */
    boolean isProjectDataset(UUID datasetId);

    /**
     * Find a dataset by its original dataset id and version.
     * @param originalDatasetId
     * @param version
     * @return the dataset or empty if not found
     */
    Optional<Dataset> findDatasetByOriginalDatasetAndVersion(UUID originalDatasetId, Integer version);

    /**
     * Find all versions of a dataset.
     * @param origineDatasetId
     * @return a list of dataset
     */
    List<Dataset> findAllDatasetVersions(UUID origineDatasetId);

    /**
     * Delete a dataset by its id.
     * @param datasetId
     */
    void deleteDatasetById(UUID datasetId);

    /**
     * Add a list of generations to a dataset.
     * @param dataset
     * @param generations
     */
    void addItemListToDataset(Dataset dataset, Set<Generation> generations);

    /**
     * Remove a list of generations from a dataset.
     * @param dataset
     * @param generations
     */
    void removeItemListFromDataset(Dataset dataset, Set<Generation> generations);

    /**
     * Find all datasets that match the given filters.
     * @param type
     * @param version
     * @param name
     * @param description
     * @param comment
     * @param authorTrigram
     * @param startDate
     * @param endDate
     * @param pageable
     * @return a pageable list of datasets
     */
    DatasetsPage findPagination(DatasetTypeEnum type, Integer version, String name, String description, String comment, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);

    /**
     * Add an ongoing generation to a dataset.
     * @param dataset
     * @param generation
     */
    void addOngoingGenerationToDataset(Dataset dataset, OngoingGeneration generation);

    /**
     * Remove all ongoing generation from a dataset.
     * @param dataset
     */
    void removeOngoingGenerationFromDataset(Dataset dataset);
}
