package be.unamur.fpgen.entity.statistic.view;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

public interface StatisticProjection {

    String getDatasetId();

    String getGenerationId();

    MessageTypeEnum getType();

    MessageTopicEnum getTopic();

    Integer getQuantity();
}
