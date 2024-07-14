package be.unamur.fpgen.instant_message;

import be.unamur.fpgen.interlocutor.Interlocutor;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ConversationMessage extends AbstractInstantMessage {
    private final Interlocutor sender;
    private final Interlocutor receiver;

    private ConversationMessage(Builder builder) {
        super(builder.id,
                builder.creationDate,
                builder.modificationDate,
                builder.getTopic(),
                builder.getType(),
                builder.getContent(),
                builder.getGeneration(),
                builder.isBatch());
        sender = builder.sender;
        receiver = builder.receiver;
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


    public static final class Builder extends AbstractInstantMessageBuilder<Builder>{
        private OffsetDateTime creationDate;
        private OffsetDateTime modificationDate;
        private UUID id;
        private Interlocutor sender;
        private Interlocutor receiver;

        private Builder() {
        }

        public Builder withCreationDate(OffsetDateTime val) {
            creationDate = val;
            return this;
        }

        public Builder withModificationDate(OffsetDateTime val) {
            modificationDate = val;
            return this;
        }

        public Builder withId(UUID val) {
            id = val;
            return this;
        }

        public Builder withSender(Interlocutor val) {
            sender = val;
            return this;
        }

        public Builder withReceiver(Interlocutor val) {
            receiver = val;
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
