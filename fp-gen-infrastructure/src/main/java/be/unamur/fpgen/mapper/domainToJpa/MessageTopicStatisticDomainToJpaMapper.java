package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.statistic.MessageTypeTopicStatisticEntity;
import be.unamur.fpgen.entity.statistic.StatisticEntity;
import be.unamur.fpgen.statistic.MessageTypeTopicStatistic;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class MessageTopicStatisticDomainToJpaMapper {
    public static MessageTypeTopicStatisticEntity mapForCreate(MessageTypeTopicStatistic domain, StatisticEntity statistic) {
        final MessageTypeTopicStatisticEntity entity = new MessageTypeTopicStatisticEntity();
        entity.setStatistic(statistic);
        entity.setMessageType(domain.getMessageType());
        entity.setMessageTopic(domain.getMessageTopic());
        entity.setRatio(domain.getRatio());
        return entity;
    }
}
