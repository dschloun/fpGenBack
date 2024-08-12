package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.statistic.MessageTopicStatisticEntity;
import be.unamur.fpgen.statistic.MessageTopicStatistic;

public class MessageTopicStatisticJpaToDomainMapper {
    public static MessageTopicStatistic map(MessageTopicStatisticEntity entity) {
        return MessageTopicStatistic.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withMessageTopic(entity.getMessageTopic())
                .withRatio(entity.getRatio())
                .build();
    }
}

