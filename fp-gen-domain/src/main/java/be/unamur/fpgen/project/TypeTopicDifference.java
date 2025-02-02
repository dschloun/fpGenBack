package be.unamur.fpgen.project;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

/**
 * This class represents the difference between the number of messages of a given type and topic.
 * within a given dataset
 */
public class TypeTopicDifference {
    private final String key;
    private final MessageTypeEnum type;
    private final MessageTopicEnum topic;
    private final Integer difference;

    private TypeTopicDifference(Builder builder) {
        key = builder.key;
        type = builder.type;
        topic = builder.topic;
        difference = builder.difference;
    }

    public String getKey() {
        return key;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public MessageTopicEnum getTopic() {
        return topic;
    }

    public Integer getDifference() {
        return difference;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String key;
        private MessageTypeEnum type;
        private MessageTopicEnum topic;
        private Integer difference;

        private Builder() {
        }

        public Builder withKey(String val) {
            key = val;
            return this;
        }

        public Builder withType(MessageTypeEnum val) {
            type = val;
            return this;
        }

        public Builder withTopic(MessageTopicEnum val) {
            topic = val;
            return this;
        }

        public Builder withDifference(Integer val) {
            difference = val;
            return this;
        }

        public TypeTopicDifference build() {
            return new TypeTopicDifference(this);
        }
    }
}
