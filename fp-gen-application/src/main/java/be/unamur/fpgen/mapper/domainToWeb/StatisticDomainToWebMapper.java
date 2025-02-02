package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.statistic.MessageTypeTopicStatistic;
import be.unamur.model.*;

import java.util.Objects;
import java.util.Set;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class StatisticDomainToWebMapper {
    private static TopicRatioCouple map(MessageTypeTopicStatistic domain) {
        return new TopicRatioCouple()
                .messageTopic(MessageTopicDomainToWebMapper.map(domain.getMessageTopic()))
                .ratio(domain.getRatio().doubleValue());
    }

    private static  MessageTypeTopicRatios map(Set<MessageTypeTopicStatistic> domain, MessageTypeEnum messageType) {
        return new MessageTypeTopicRatios()
                .messageType(MessageTypeDomainToWebMapper.map(messageType))
                .messageTopicRatioList(domain
                        .stream()
                        .filter(d -> messageType.equals(d.getMessageType()))
                        .map(StatisticDomainToWebMapper::map)
                        .toList());
    }

    public static Statistic map(be.unamur.fpgen.statistic.Statistic domain) {
        if (Objects.isNull(domain)){
            return null;
        }
        return new Statistic()
                .id(domain.getId())
                .fakeRealRatios(new FakeRealRatios()
                        .fakeRatio(domain.getFakeRatio().doubleValue())
                        .realRatio(domain.getRealRatio().doubleValue()))
                .typeRatios(new TypeRatios()
                        .socialEngineeringRatio(domain.getSocialEngineerRatio().doubleValue())
                        .harassmentRatio(domain.getHarasserRatio().doubleValue())
                        .genuineRatio(domain.getRealRatio().doubleValue()))
                .genuineTopicRatios(map(domain.getGenuineTopicStatisticList(), MessageTypeEnum.GENUINE))
                .socialEngineeringTopicRatios(map(domain.getSocialEngineeringTopicStatisticList(), MessageTypeEnum.SOCIAL_ENGINEERING))
                .harassmentTopicRatios(map(domain.getHarassmentTopicStatisticList(), MessageTypeEnum.HARASSMENT));
    }
}
