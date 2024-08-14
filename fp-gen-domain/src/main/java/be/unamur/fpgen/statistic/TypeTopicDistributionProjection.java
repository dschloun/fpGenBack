package be.unamur.fpgen.statistic;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

public interface TypeTopicDistributionProjection {

//    String getDatasetId();
//
//    String getGenerationId();

    MessageTypeEnum getType();

    MessageTopicEnum getTopic();

    Integer getQuantity();
}
