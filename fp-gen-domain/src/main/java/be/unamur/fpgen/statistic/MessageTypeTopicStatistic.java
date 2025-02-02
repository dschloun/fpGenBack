package be.unamur.fpgen.statistic;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.math.BigDecimal;

/**
 * This class represents the type/topic distribution
 */
public class MessageTypeTopicStatistic extends BaseUuidDomain {
     private final MessageTypeEnum messageType;
     private final MessageTopicEnum messageTopic;
     private final BigDecimal ratio;

    private MessageTypeTopicStatistic(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        messageType = builder.messageType;
        messageTopic = builder.messageTopic;
        ratio = builder.ratio;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private MessageTypeEnum messageType;
        private MessageTopicEnum messageTopic;
        private BigDecimal ratio;

        private Builder() {
        }

        public Builder withMessageType(MessageTypeEnum val){
            messageType = val;
            return this;
        }

        public Builder withMessageTopic(MessageTopicEnum val) {
            messageTopic = val;
            return this;
        }

        public Builder withRatio(BigDecimal val) {
            ratio = val;
            return this;
        }

        public MessageTypeTopicStatistic build() {
            return new MessageTypeTopicStatistic(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
