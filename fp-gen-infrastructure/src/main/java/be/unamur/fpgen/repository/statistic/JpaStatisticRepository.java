package be.unamur.fpgen.repository.statistic;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.mapper.domainToJpa.StatisticDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.StatisticJpaToDomainMapper;
import be.unamur.fpgen.repository.ResultRepository;
import be.unamur.fpgen.repository.StatisticRepository;
import be.unamur.fpgen.repository.dataset.JpaDatasetRepositoryCRUD;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.statistic.TypeTopicDistributionProjection;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * See the specifications in the {@link StatisticRepository} interface.
 */
@Repository
public class JpaStatisticRepository implements StatisticRepository {
    private final JpaStatisticRepositoryCRUD jpaStatisticRepositoryCRUD;
    private final JpaDatasetRepositoryCRUD jpaDatasetRepositoryCRUD;

    public JpaStatisticRepository(JpaStatisticRepositoryCRUD jpaStatisticRepositoryCRUD, JpaDatasetRepositoryCRUD jpaDatasetRepositoryCRUD) {
        this.jpaStatisticRepositoryCRUD = jpaStatisticRepositoryCRUD;
        this.jpaDatasetRepositoryCRUD = jpaDatasetRepositoryCRUD;
    }

    @Override
    public Integer findTotal(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findTotalByDatasetId(datasetId.toString()).orElse(0);
    }

    @Override
    public Integer findHarassmentTotal(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findHarassmentTotalByDatasetId(datasetId.toString()).orElse(0);
    }

    @Override
    public Integer findSocialEngineeringTotal(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findSocialEngineeringTotalByDatasetId(datasetId.toString()).orElse(0);
    }

    @Override
    public Integer findGenuineTotal(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findGenuineTotalByDatasetId(datasetId.toString()).orElse(0);
    }

    @Override
    public List<TypeTopicDistributionProjection> findTypeTopicDistribution(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findTypeTopicDistributionByDatasetId(datasetId.toString());
    }

    @Override
    public void save(Statistic statistic, Dataset dataset) {
        final DatasetEntity datasetEntity = jpaDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
        datasetEntity.setStatistic(StatisticDomainToJpaMapper.mapForCreate(statistic, datasetEntity));
        jpaDatasetRepositoryCRUD.save(datasetEntity);
    }

    @Override
    public Optional<Statistic> findStatisticByDatasetId(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findByDatasetId(datasetId)
                .map(StatisticJpaToDomainMapper::map);
    }

    @Override
    public void deleteByDataset(Dataset dataset) {
        final DatasetEntity datasetEntity = jpaDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
        datasetEntity.setStatistic(null);
        jpaDatasetRepositoryCRUD.save(datasetEntity);

    }
}
