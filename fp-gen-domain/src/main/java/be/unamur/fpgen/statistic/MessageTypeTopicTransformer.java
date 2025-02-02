package be.unamur.fpgen.statistic;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class is useful to transform in a format usable for the frontend
 */
public class MessageTypeTopicTransformer {

    public static MessageTypeTopicStatistic transform(TypeTopicDistributionProjection projection, Integer total){
        return MessageTypeTopicStatistic.newBuilder()
                .withMessageType(projection.getType())
                .withMessageTopic(projection.getTopic())
                .withRatio(BigDecimal.valueOf(projection.getQuantity()).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP))
                .build();
    }
}
