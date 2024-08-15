package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.statistic.TypeTopicDistributionProjection;
import be.unamur.fpgen.mapper.domainToJpa.StatisticDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.StatisticJpaToDomainMapper;
import be.unamur.fpgen.statistic.Statistic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaStatisticRepository implements StatisticRepository {
    private final JpaStatisticRepositoryCRUD jpaStatisticRepositoryCRUD;
    private final JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD;
    private final JpaConversationDatasetRepositoryCRUD jpaConversationDatasetRepositoryCRUD;

    public JpaStatisticRepository(JpaStatisticRepositoryCRUD jpaStatisticRepositoryCRUD, JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD, JpaConversationDatasetRepositoryCRUD jpaConversationDatasetRepositoryCRUD) {
        this.jpaStatisticRepositoryCRUD = jpaStatisticRepositoryCRUD;
        this.jpaInstantMessageDatasetRepositoryCRUD = jpaInstantMessageDatasetRepositoryCRUD;
        this.jpaConversationDatasetRepositoryCRUD = jpaConversationDatasetRepositoryCRUD;
    }

    @Override
    public Integer findTotal(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findTotalByDatasetId(datasetId.toString()).orElse(0);
    }

    @Override
    public Integer findTrollingTotal(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findTrollingTotalByDatasetId(datasetId.toString()).orElse(0);
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
    public void save(Statistic statistic, AbstractDataset dataset) {
        if (dataset instanceof InstantMessageDataset){
            final InstantMessageDatasetEntity datasetEntity = jpaInstantMessageDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
            datasetEntity.setStatistic(StatisticDomainToJpaMapper.mapForCreate(statistic, datasetEntity));
            jpaInstantMessageDatasetRepositoryCRUD.save(datasetEntity);
        } else {
            final ConversationDatasetEntity datasetEntity = jpaConversationDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
            datasetEntity.setStatistic(StatisticDomainToJpaMapper.mapForCreate(statistic, datasetEntity));
            jpaConversationDatasetRepositoryCRUD.save(datasetEntity);
        }
    }

    @Override
    public Optional<Statistic> findStatisticByDatasetId(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findByDatasetId(datasetId)
                .map(StatisticJpaToDomainMapper::map);
    }

    @Override
    public void deleteByDataset(AbstractDataset dataset) {
        if (dataset instanceof InstantMessageDataset){
            final InstantMessageDatasetEntity datasetEntity = jpaInstantMessageDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
            datasetEntity.setStatistic(null);
            jpaInstantMessageDatasetRepositoryCRUD.save(datasetEntity);
        } else {
            final ConversationDatasetEntity datasetEntity = jpaConversationDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
            datasetEntity.setStatistic(null);
            jpaConversationDatasetRepositoryCRUD.save(datasetEntity);
        }
    }
}
