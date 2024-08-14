package be.unamur.fpgen.statistic;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

public class TypeTopicDistributionProjection {
    private MessageTypeEnum messageType;
    private MessageTopicEnum messageTopic;
    private Integer messageQuantity;

    public TypeTopicDistributionProjection(MessageTypeEnum messageType, MessageTopicEnum messageTopic, Integer messageQuantity) {
        this.messageType = messageType;
        this.messageTopic = messageTopic;
        this.messageQuantity = messageQuantity;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public void setMessageTopic(MessageTopicEnum messageTopic) {
        this.messageTopic = messageTopic;
    }

    public Integer getMessageQuantity() {
        return messageQuantity;
    }

    public void setMessageQuantity(Integer messageQuantity) {
        this.messageQuantity = messageQuantity;
    }
}
