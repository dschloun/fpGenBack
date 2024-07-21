package be.unamur.fpgen.conversation.pagination;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.time.OffsetDateTime;

public class ConversationQuery {

    private final MessageTypeEnum messageType;
    private final MessageTopicEnum messageTopic;
    private final Integer maxInteractionNumber;
    private final Integer minInteractionNumber;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    private ConversationQuery(Builder builder) {
        messageType = builder.messageType;
        messageTopic = builder.messageTopic;
        maxInteractionNumber = builder.maxInteractionNumber;
        minInteractionNumber = builder.minInteractionNumber;
        startDate = builder.startDate;
        endDate = builder.endDate;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public Integer getMaxInteractionNumber() {
        return maxInteractionNumber;
    }

    public Integer getMinInteractionNumber() {
        return minInteractionNumber;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public static final class Builder {
        private MessageTypeEnum messageType;
        private MessageTopicEnum messageTopic;
        private Integer maxInteractionNumber;
        private Integer minInteractionNumber;
        private OffsetDateTime startDate;
        private OffsetDateTime endDate;

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

        public Builder withMaxInteractionNumber(Integer val) {
            maxInteractionNumber = val;
            return this;
        }

        public Builder withMinInteractionNumber(Integer val) {
            minInteractionNumber = val;
            return this;
        }

        public Builder withStartDate(OffsetDateTime val) {
            startDate = val;
            return this;
        }

        public Builder withEndDate(OffsetDateTime val) {
            endDate = val;
            return this;
        }

        public ConversationQuery build() {
            return new ConversationQuery(this);
        }
    }
}
