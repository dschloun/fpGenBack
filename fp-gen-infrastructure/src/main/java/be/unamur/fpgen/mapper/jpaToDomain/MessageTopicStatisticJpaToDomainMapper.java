package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.statistic.MessageTypeTopicStatisticEntity;
import be.unamur.fpgen.statistic.MessageTypeTopicStatistic;

public class MessageTopicStatisticJpaToDomainMapper {
    public static MessageTypeTopicStatistic map(MessageTypeTopicStatisticEntity entity) {
        return MessageTypeTopicStatistic.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withMessageType(entity.getMessageType())
                .withMessageTopic(entity.getMessageTopic())
                .withRatio(entity.getRatio())
                .build();
    }
}

