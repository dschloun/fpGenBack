package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.statistic.MessageTypeTopicStatisticEntity;
import be.unamur.fpgen.statistic.MessageTypeTopicStatistic;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
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

