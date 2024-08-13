package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.statistic.MessageTypeTopicStatisticEntity;
import be.unamur.fpgen.statistic.MessageTypeTopicStatistic;

public class MessageTopicStatisticDomainToJpaMapper {
    public static MessageTypeTopicStatisticEntity mapForCreate(MessageTypeTopicStatistic domain) {
        final MessageTypeTopicStatisticEntity entity = new MessageTypeTopicStatisticEntity();
        entity.setMessageType(domain.getMessageType());
        entity.setMessageTopic(domain.getMessageTopic());
        entity.setRatio(domain.getRatio());
        return entity;
    }
}
