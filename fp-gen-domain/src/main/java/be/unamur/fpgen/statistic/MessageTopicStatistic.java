package be.unamur.fpgen.statistic;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.message.MessageTopicEnum;

import java.time.OffsetDateTime;
import java.util.UUID;

public class MessageTopicStatistic extends BaseUuidDomain {
     private final MessageTopicEnum messageTopic;
     private final double ratio;

    private MessageTopicStatistic(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        messageTopic = builder.messageTopic;
        ratio = builder.ratio;
    }

    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public double getRatio() {
        return ratio;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private MessageTopicEnum messageTopic;
        private double ratio;

        private Builder() {
        }


        public Builder withMessageTopic(MessageTopicEnum val) {
            messageTopic = val;
            return this;
        }

        public Builder withRatio(double val) {
            ratio = val;
            return this;
        }

        public MessageTopicStatistic build() {
            return new MessageTopicStatistic(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
