package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.statistic.MessageTypeTopicStatistic;
import be.unamur.model.*;

import java.util.Set;

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
        return new Statistic()
                .id(domain.getId())
                .fakeRealRatios(new FakeRealRatios()
                        .fakeRatio(domain.getFakeRatio().doubleValue())
                        .realRatio(domain.getRealRatio().doubleValue()))
                .typeRatios(new TypeRatios()
                        .socialEngineeringRatio(domain.getSocialEngineerRatio().doubleValue())
                        .trollingRatio(domain.getTrollRatio().doubleValue())
                        .genuineRatio(domain.getRealRatio().doubleValue()))
                .genuineTopicRatios(map(domain.getMessageTopicStatisticList(), MessageTypeEnum.GENUINE))
                .socialEngineeringTopicRatios(map(domain.getMessageTopicStatisticList(), MessageTypeEnum.SOCIAL_ENGINEERING))
                .trollingTopicRatios(map(domain.getMessageTopicStatisticList(), MessageTypeEnum.TROLLING));
    }
}
