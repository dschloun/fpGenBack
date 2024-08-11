package be.unamur.fpgen.generation.ongoing_generation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

public class OngoingGenerationItem extends BaseUuidDomain {

    private final MessageTypeEnum messageType;
    private final MessageTopicEnum messageTopic;
    private final Integer quantity;
    private OngoingItemStatus status;

    private OngoingGenerationItem(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        messageType = builder.messageType;
        messageTopic = builder.messageTopic;
        quantity = builder.quantity;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OngoingItemStatus getStatus() {
        return status;
    }

    public void updateStatus(OngoingItemStatus status) {
        this.status = status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder> {
        private MessageTypeEnum messageType;
        private MessageTopicEnum messageTopic;
        private Integer quantity;
        private OngoingItemStatus status;

        private Builder() {
        }


        public Builder withMessageType(MessageTypeEnum val) {
            messageType = val;
            return this;
        }

        public Builder withMessageTopic(MessageTopicEnum val) {
            messageTopic = val;
            return this;
        }

        public Builder withQuantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder withStatus(OngoingItemStatus val) {
            status = val;
            return this;
        }

        public OngoingGenerationItem build() {
            return new OngoingGenerationItem(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
