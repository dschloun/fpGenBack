package be.unamur.fpgen.statistic;

import java.math.BigDecimal;

public class MessageTypeTopicTransformer {

    public static MessageTypeTopicStatistic transform(TypeTopicDistributionProjection projection, Integer total){
        return MessageTypeTopicStatistic.newBuilder()
                .withMessageType(projection.getType())
                .withMessageTopic(projection.getTopic())
                .withRatio(BigDecimal.valueOf(projection.getQuantity() / total))
                .build();
    }
}
