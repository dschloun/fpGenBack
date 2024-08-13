package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.statistic.StatisticEntity;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.utils.MapperUtil;

public class StatisticDomainToJpaMapper {
    public static StatisticEntity mapForCreate(Statistic domain, DatasetEntity dataset) {
        final StatisticEntity entity = new StatisticEntity();
        entity.setTotal(domain.getTotal());
        entity.setFakeRatio(domain.getFakeRatio());
        entity.setRealRatio(domain.getRealRatio());
        entity.setSocialEngineerRatio(domain.getSocialEngineerRatio());
        entity.setTrollRatio(domain.getTrollRatio());
        entity.setMessageTopicStatisticList(MapperUtil
                .mapSet(domain.getMessageTopicStatisticList(),
                        d -> MessageTopicStatisticDomainToJpaMapper.mapForCreate(d, entity)));
        entity.setDataset(dataset);
        return entity;
    }
}
