package be.unamur.fpgen.dataset;

import be.unamur.fpgen.message.MessageTopicEnum;

/**
 * This class represents the bias between the number of real and fake messages for a given topic.
 * The bias is the difference between the number of real and fake messages.
 */
public class RealFakeTopicBias {
    private final MessageTopicEnum topic;
    private final Integer realNumber;
    private final Integer fakeNumber;
    private final Integer bias;

    private RealFakeTopicBias(Builder builder) {
        topic = builder.topic;
        realNumber = builder.realNumber;
        fakeNumber = builder.fakeNumber;
        bias = builder.bias;
    }

    public MessageTopicEnum getTopic() {
        return topic;
    }

    public Integer getRealNumber() {
        return realNumber;
    }

    public Integer getFakeNumber() {
        return fakeNumber;
    }

    public Integer getBias() {
        return bias;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private MessageTopicEnum topic;
        private Integer realNumber;
        private Integer fakeNumber;
        private Integer bias;

        private Builder() {
        }

        public Builder withTopic(MessageTopicEnum val) {
            topic = val;
            return this;
        }

        public Builder withRealNumber(Integer val) {
            realNumber = val;
            return this;
        }

        public Builder withFakeNumber(Integer val) {
            fakeNumber = val;
            return this;
        }

        public Builder withBias(Integer val) {
            bias = val;
            return this;
        }

        public RealFakeTopicBias build() {
            return new RealFakeTopicBias(this);
        }
    }
}
