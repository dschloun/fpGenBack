package be.unamur.fpgen.message;

import be.unamur.fpgen.interlocutor.Interlocutor;

import java.util.UUID;

public class ConversationMessage extends AbstractInstantMessage {
    private final Interlocutor sender;
    private final Interlocutor receiver;
    private final UUID conversationId;

    private ConversationMessage(Builder builder) {
        super(builder.getId(),
                builder.getCreationDate(),
                builder.getModificationDate(),
                builder.getTopic(),
                builder.getType(),
                builder.getContent(),
                builder.getGeneration(),
                builder.isBatch());
        sender = builder.sender;
        receiver = builder.receiver;
        conversationId = builder.conversationId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Interlocutor getSender() {
        return sender;
    }

    public Interlocutor getReceiver() {
        return receiver;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public static final class Builder extends AbstractInstantMessageBuilder<Builder>{
        private Interlocutor sender;
        private Interlocutor receiver;
        private UUID conversationId;

        private Builder() {
        }

        public Builder withSender(Interlocutor val) {
            sender = val;
            return this;
        }

        public Builder withReceiver(Interlocutor val) {
            receiver = val;
            return this;
        }

        public Builder withConversationId(UUID val) {
            conversationId = val;
            return this;
        }

        public ConversationMessage build() {
            return new ConversationMessage(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
