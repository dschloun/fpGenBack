package be.unamur.fpgen.message.pagination.InstantMessage;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.time.OffsetDateTime;

public class InstantMessageQuery {

    private final MessageTypeEnum messageType;
    private final MessageTopicEnum messageTopic;
    private final String content;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    private InstantMessageQuery(Builder builder) {
        messageType = builder.messageType;
        messageTopic = builder.messageTopic;
        content = builder.content;
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

    public String getContent() {
        return content;
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
        private String content;
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

        public Builder withContent(String val) {
            content = val;
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

        public InstantMessageQuery build() {
            return new InstantMessageQuery(this);
        }
    }
}
