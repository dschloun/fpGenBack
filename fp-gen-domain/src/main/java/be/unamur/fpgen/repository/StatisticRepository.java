package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.statistic.Statistic;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StatisticRepository {

    Integer findTotal(UUID datasetId);

    Integer findTrollingTotal(UUID datasetId);

    Integer findSocialEngineeringTotal(UUID datasetId);

    Integer findGenuineTotal(UUID datasetId);

    List<Triple<MessageTypeEnum, MessageTopicEnum, Integer>> findTypeTopicDistribution(UUID datasetId);

    void save(Statistic statistic, AbstractDataset dataset);

    Optional<Statistic> findStatisticByDatasetId(UUID datasetId);
}
