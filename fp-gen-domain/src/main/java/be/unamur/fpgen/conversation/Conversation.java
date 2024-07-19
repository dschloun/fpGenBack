package be.unamur.fpgen.conversation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.instant_message.ConversationMessage;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Conversation extends BaseUuidDomain {
    private final MessageTypeEnum type;
    private final MessageTopicEnum topic;
    private final Integer maxInteractionNumber;
    private final Integer minInteractionNumber;
    private final Set<ConversationMessage> conversationMessageList = new HashSet<>();
    private final UUID generationId;

    private Conversation(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        type = builder.type;
        topic = builder.topic;
        maxInteractionNumber = builder.maxInteractionNumber;
        minInteractionNumber = builder.minInteractionNumber;
        conversationMessageList.addAll(builder.conversationMessageList);
        generationId = builder.generationId;
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

    public Integer getMinInteractionNumber() {
        return minInteractionNumber;
    }

    public Set<ConversationMessage> getConversationMessageList() {
        return conversationMessageList;
    }

    public UUID getGenerationId() {
        return generationId;
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder> {
        private MessageTypeEnum type;
        private MessageTopicEnum topic;
        private Integer maxInteractionNumber;
        private Integer minInteractionNumber;
        private Set<ConversationMessage> conversationMessageList = new HashSet<>();
        private UUID generationId;

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

        public Builder withMinInteractionNumber(Integer val) {
            minInteractionNumber = val;
            return this;
        }

        public Builder withConversationMessageList(Set<ConversationMessage> val) {
            conversationMessageList = val;
            return this;
        }

        public Builder withGenerationId(UUID val) {
            generationId = val;
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
