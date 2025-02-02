package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.statistic.StatisticEntity;
import be.unamur.fpgen.statistic.MessageTypeTopicStatistic;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.utils.MapperUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class StatisticDomainToJpaMapper {
    public static StatisticEntity mapForCreate(Statistic domain, DatasetEntity dataset) {
        final Set<MessageTypeTopicStatistic> set = new HashSet<>();
        set.addAll(domain.getGenuineTopicStatisticList());
        set.addAll(domain.getSocialEngineeringTopicStatisticList());
        set.addAll(domain.getHarassmentTopicStatisticList());

        final StatisticEntity entity = new StatisticEntity();
        entity.setTotal(domain.getTotal());
        entity.setFakeRatio(domain.getFakeRatio());
        entity.setRealRatio(domain.getRealRatio());
        entity.setSocialEngineerRatio(domain.getSocialEngineerRatio());
        entity.setHarasserRatio(domain.getHarasserRatio());
        entity.setMessageTopicStatisticList(MapperUtil
                .mapSet(set,
                        d -> MessageTopicStatisticDomainToJpaMapper.mapForCreate(d, entity)));
        entity.setDataset(dataset);
        return entity;
    }
}
