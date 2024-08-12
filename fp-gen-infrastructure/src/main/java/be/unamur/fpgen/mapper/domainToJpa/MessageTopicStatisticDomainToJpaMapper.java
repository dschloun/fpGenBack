package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.statistic.MessageTopicStatisticEntity;
import be.unamur.fpgen.statistic.MessageTopicStatistic;

public class MessageTopicStatisticDomainToJpaMapper {
    public static MessageTopicStatisticEntity mapForCreate(MessageTopicStatistic domain) {
        final MessageTopicStatisticEntity entity = new MessageTopicStatisticEntity();
        entity.setMessageTopic(domain.getMessageTopic());
        entity.setRatio(domain.getRatio());
        return entity;
    }
}
