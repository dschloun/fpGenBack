package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.statistic.TypeTopicDistributionProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Statistic
 */
public interface StatisticRepository {

    /**
     * Find the total number of messages in the dataset
     *
     * @param datasetId the dataset id
     * @return the total number of messages
     */
    Integer findTotal(UUID datasetId);

    /**
     * Find the total number of harassment messages in the dataset
     *
     * @param datasetId the dataset id
     * @return the total number of harassment messages
     */
    Integer findHarassmentTotal(UUID datasetId);

    /**
     * Find the total number of social engineering messages in the dataset
     *
     * @param datasetId the dataset id
     * @return the total number of social engineering messages
     */
    Integer findSocialEngineeringTotal(UUID datasetId);

    /**
     * Find the total number of genuine messages in the dataset
     *
     * @param datasetId the dataset id
     * @return the total number of genuine messages
     */
    Integer findGenuineTotal(UUID datasetId);

    /**
     * Find type topic distribution for a given dataset
     * @param datasetId the dataset id
     */
    List<TypeTopicDistributionProjection> findTypeTopicDistribution(UUID datasetId);

    /**
     * Save a statistic
     * @param statistic the statistic to save
     * @param dataset the dataset to which the statistic belongs
     */
    void save(Statistic statistic, Dataset dataset);

    /**
     * Find a statistic by dataset id
     * @param datasetId the dataset id
     * @return the statistic
     */
    Optional<Statistic> findStatisticByDatasetId(UUID datasetId);

    /**
     * Delete a statistic by dataset
     * @param dataset the dataset
     */
    void deleteByDataset(Dataset dataset);
}
