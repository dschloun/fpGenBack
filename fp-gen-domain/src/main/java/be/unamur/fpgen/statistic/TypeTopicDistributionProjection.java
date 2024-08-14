package be.unamur.fpgen.statistic;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

public interface TypeTopicDistributionProjection {

    MessageTypeEnum getType();

    MessageTopicEnum getTopic();

    Integer getQuantity();
}
