package be.unamur.fpgen.conversation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.instant_message.ConversationMessage;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;

import java.util.List;
import java.util.Set;

public class Conversation extends BaseUuidDomain {
    private final MessageTypeEnum type;
    private final MessageTopicEnum topic;
    private final Integer maxInteractionNumber;
    private final Set<ConversationMessage> conversationMessageList;

    private Conversation(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        type = builder.type;
        topic = builder.topic;
        maxInteractionNumber = builder.maxInteractionNumber;
        conversationMessageList = builder.conversationMessageList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public MessageTopicEnum getTopic() {
        return topic;
    }

    public Integer getMaxInteractionNumber() {
        return maxInteractionNumber;
    }

    public Set<ConversationMessage> getConversationMessageList() {
        return conversationMessageList;
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder> {
        private MessageTypeEnum type;
        private MessageTopicEnum topic;
        private Integer maxInteractionNumber;
        private Set<ConversationMessage> conversationMessageList;

        private Builder() {
        }

        public Builder withType(MessageTypeEnum val) {
            type = val;
            return this;
        }

        public Builder withTopic(MessageTopicEnum val) {
            topic = val;
            return this;
        }

        public Builder withMaxInteractionNumber(Integer val) {
            maxInteractionNumber = val;
            return this;
        }

        public Builder withConversationMessageList(Set<ConversationMessage> val) {
            conversationMessageList = val;
            return this;
        }

        public Conversation build() {
            return new Conversation(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
