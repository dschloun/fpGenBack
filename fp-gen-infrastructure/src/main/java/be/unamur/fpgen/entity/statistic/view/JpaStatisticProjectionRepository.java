package be.unamur.fpgen.entity.statistic.view;

import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.mapper.domainToJpa.StatisticDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.StatisticJpaToDomainMapper;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.repository.JpaConversationDatasetRepositoryCRUD;
import be.unamur.fpgen.repository.JpaInstantMessageDatasetRepositoryCRUD;
import be.unamur.fpgen.repository.StatisticRepository;
import be.unamur.fpgen.statistic.Statistic;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaStatisticProjectionRepository implements StatisticRepository {
    private final JpaStatisticProjectionRepositoryCRUD jpaStatisticProjectionRepositoryCRUD;
    private final JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD;
    private final JpaConversationDatasetRepositoryCRUD jpaConversationDatasetRepositoryCRUD;

    public JpaStatisticProjectionRepository(JpaStatisticProjectionRepositoryCRUD jpaStatisticProjectionRepositoryCRUD, JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD, JpaConversationDatasetRepositoryCRUD jpaConversationDatasetRepositoryCRUD) {
        this.jpaStatisticProjectionRepositoryCRUD = jpaStatisticProjectionRepositoryCRUD;
        this.jpaInstantMessageDatasetRepositoryCRUD = jpaInstantMessageDatasetRepositoryCRUD;
        this.jpaConversationDatasetRepositoryCRUD = jpaConversationDatasetRepositoryCRUD;
    }

    @Override
    public Integer findTotal(UUID datasetId) {
        return jpaStatisticProjectionRepositoryCRUD.findTotalByDatasetId(datasetId.toString());
    }

    @Override
    public Integer findTrollingTotal(UUID datasetId) {
        return jpaStatisticProjectionRepositoryCRUD.findTrollingTotalByDatasetId(datasetId.toString());
    }

    @Override
    public Integer findSocialEngineeringTotal(UUID datasetId) {
        return jpaStatisticProjectionRepositoryCRUD.findSocialEngineeringTotalByDatasetId(datasetId.toString());
    }

    @Override
    public Integer findGenuineTotal(UUID datasetId) {
        return jpaStatisticProjectionRepositoryCRUD.findGenuineTotalByDatasetId(datasetId.toString());
    }

    @Override
    public List<Triple<MessageTypeEnum, MessageTopicEnum, Integer>> findTypeTopicDistribution(UUID datasetId) {
        return jpaStatisticProjectionRepositoryCRUD.findTypeTopicDistributionByDatasetId(datasetId.toString());
    }

    @Override
    public void save(Statistic statistic, AbstractDataset dataset) {
        DatasetEntity datasetEntity;
        if (dataset instanceof InstantMessageDataset){
            datasetEntity = jpaInstantMessageDatasetRepositoryCRUD.getReferenceById(dataset.getId());
        } else {
            datasetEntity = jpaConversationDatasetRepositoryCRUD.getReferenceById(dataset.getId());
        }
        jpaStatisticProjectionRepositoryCRUD.save(StatisticDomainToJpaMapper.mapForCreate(statistic, datasetEntity));
    }

    @Override
    public Optional<Statistic> findStatisticByDatasetId(UUID datasetId) {
        return jpaStatisticProjectionRepositoryCRUD.findByDatasetId(datasetId)
                .map(StatisticJpaToDomainMapper::map);
    }
}
