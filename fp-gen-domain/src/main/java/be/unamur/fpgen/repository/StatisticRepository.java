package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.statistic.TypeTopicDistributionProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StatisticRepository {

    Integer findTotal(UUID datasetId);

    Integer findTrollingTotal(UUID datasetId);

    Integer findSocialEngineeringTotal(UUID datasetId);

    Integer findGenuineTotal(UUID datasetId);

    List<TypeTopicDistributionProjection> findTypeTopicDistribution(UUID datasetId);

    void save(Statistic statistic, Dataset dataset);

    Optional<Statistic> findStatisticByDatasetId(UUID datasetId);

    void deleteByDataset(Dataset dataset);
}
