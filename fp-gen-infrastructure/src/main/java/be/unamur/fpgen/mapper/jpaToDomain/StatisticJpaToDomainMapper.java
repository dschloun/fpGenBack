package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.statistic.StatisticEntity;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.utils.MapperUtil;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class StatisticJpaToDomainMapper {
    public static Statistic map(StatisticEntity entity) {
        if(Objects.isNull(entity)) {
            return null;
        }
        return Statistic.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withTotal(entity.getTotal())
                .withFakeRatio(entity.getFakeRatio())
                .withRealRatio(entity.getRealRatio())
                .withSocialEngineerRatio(entity.getSocialEngineerRatio())
                .withHarasserRatio(entity.getHarasserRatio())
                .withGenuineTopicStatisticList(MapperUtil
                        .mapSet(entity.getMessageTopicStatisticList().stream().filter(e -> MessageTypeEnum.GENUINE.equals(e.getMessageType())).collect(Collectors.toSet()),
                                MessageTopicStatisticJpaToDomainMapper::map))
                .withSocialEngineeringTopicStatisticList(MapperUtil
                        .mapSet(entity.getMessageTopicStatisticList().stream().filter(e -> MessageTypeEnum.SOCIAL_ENGINEERING.equals(e.getMessageType())).collect(Collectors.toSet()),
                                MessageTopicStatisticJpaToDomainMapper::map))
                .withHarassmentTopicStatisticList(MapperUtil
                        .mapSet(entity.getMessageTopicStatisticList().stream().filter(e -> MessageTypeEnum.HARASSMENT.equals(e.getMessageType())).collect(Collectors.toSet()),
                                MessageTopicStatisticJpaToDomainMapper::map))
                .build();
    }
}
