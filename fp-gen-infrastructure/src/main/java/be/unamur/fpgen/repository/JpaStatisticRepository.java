package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.mapper.domainToJpa.StatisticDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.StatisticJpaToDomainMapper;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.statistic.TypeTopicDistributionProjection;
import org.apache.commons.lang3.tuple.Triple;
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
        return jpaStatisticRepositoryCRUD.findTotalByDatasetId(datasetId.toString());
    }

    @Override
    public Integer findTrollingTotal(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findTrollingTotalByDatasetId(datasetId.toString());
    }

    @Override
    public Integer findSocialEngineeringTotal(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findSocialEngineeringTotalByDatasetId(datasetId.toString());
    }

    @Override
    public Integer findGenuineTotal(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findGenuineTotalByDatasetId(datasetId.toString());
    }

    @Override
    public List<TypeTopicDistributionProjection> findTypeTopicDistribution(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findTypeTopicDistributionByDatasetId(datasetId.toString());
    }

    @Override
    public void save(Statistic statistic, AbstractDataset dataset) {
        DatasetEntity datasetEntity;
        if (dataset instanceof InstantMessageDataset){
            datasetEntity = jpaInstantMessageDatasetRepositoryCRUD.getReferenceById(dataset.getId());
        } else {
            datasetEntity = jpaConversationDatasetRepositoryCRUD.getReferenceById(dataset.getId());
        }
        jpaStatisticRepositoryCRUD.save(StatisticDomainToJpaMapper.mapForCreate(statistic, datasetEntity));
    }

    @Override
    public Optional<Statistic> findStatisticByDatasetId(UUID datasetId) {
        return jpaStatisticRepositoryCRUD.findByDatasetId(datasetId)
                .map(StatisticJpaToDomainMapper::map);
    }
}
