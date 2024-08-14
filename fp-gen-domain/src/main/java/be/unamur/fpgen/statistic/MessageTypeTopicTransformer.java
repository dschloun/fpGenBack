package be.unamur.fpgen.statistic;

import java.math.BigDecimal;

public class MessageTypeTopicTransformer {

    public static MessageTypeTopicStatistic transform(TypeTopicDistributionProjection triple, Integer total){
        return MessageTypeTopicStatistic.newBuilder()
                .withMessageType(triple.getMessageType())
                .withMessageTopic(triple.getMessageTopic())
                .withRatio(BigDecimal.valueOf(triple.getMessageQuantity() / total))
                .build();
    }
}
