package be.unamur.fpgen.message.pagination.conversation_message;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.time.OffsetDateTime;

public class ConversationMessageQuery {
    private final MessageTopicEnum topic;
    private final MessageTypeEnum type;
    private final String content;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    private ConversationMessageQuery(Builder builder) {
        topic = builder.topic;
        type = builder.type;
        content = builder.content;
        startDate = builder.startDate;
        endDate = builder.endDate;
    }

    public MessageTopicEnum getTopic() {
        return topic;
    }

    public MessageTypeEnum getType() {
        return type;
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

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private MessageTopicEnum topic;
        private MessageTypeEnum type;
        private String content;
        private OffsetDateTime startDate;
        private OffsetDateTime endDate;

        private Builder() {
        }

        public Builder withTopic(MessageTopicEnum val) {
            topic = val;
            return this;
        }

        public Builder withType(MessageTypeEnum val) {
            type = val;
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

        public ConversationMessageQuery build() {
            return new ConversationMessageQuery(this);
        }
    }
}
