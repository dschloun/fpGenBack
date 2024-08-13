package be.unamur.fpgen.statistic;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.apache.commons.lang3.tuple.Triple;

import java.math.BigDecimal;

public class MessageTypeTopicTransformer {

    public static MessageTypeTopicStatistic transform(Triple<MessageTypeEnum, MessageTopicEnum, Integer> triple, Integer total){
        return MessageTypeTopicStatistic.newBuilder()
                .withMessageType(triple.getLeft())
                .withMessageTopic(triple.getMiddle())
                .withRatio(BigDecimal.valueOf(triple.getRight() / total))
                .build();
    }
}
