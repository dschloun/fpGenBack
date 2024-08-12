package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.statistic.StatisticEntity;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.utils.MapperUtil;

public class StatisticJpaToDomainMapper {
    public static Statistic map(StatisticEntity entity) {
        return Statistic.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withTotal(entity.getTotal())
                .withFakeRatio(entity.getFakeRatio())
                .withRealRatio(entity.getRealRatio())
                .withSocialEngineerRatio(entity.getSocialEngineerRatio())
                .withTrollRatio(entity.getTrollRatio())
                .withMessageTopicStatisticList(MapperUtil
                        .mapSet(entity.getMessageTopicStatisticList(),
                                MessageTopicStatisticJpaToDomainMapper::map))
                .build();
    }
}
