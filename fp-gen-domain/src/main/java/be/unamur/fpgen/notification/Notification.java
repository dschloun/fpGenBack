package be.unamur.fpgen.notification;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;

/**
 * Represents a notification sent by an author to another author.
 */
public class Notification extends BaseUuidDomain {
    private final Author sender;
    private final Author receiver;
    private NotificationStatus status;
    private final String message;

    private Notification(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        sender = builder.sender;
        receiver = builder.receiver;
        status = builder.status;
        message = builder.message;
    }

    public Author getSender() {
        return sender;
    }

    public Author getReceiver() {
        return receiver;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void updateStatus(NotificationStatus status){
        this.status = status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private Author sender;
        private Author receiver;
        private NotificationStatus status;
        private String message;

        private Builder() {
        }

        public Builder withSender(Author val) {
            sender = val;
            return this;
        }

        public Builder withReceiver(Author val) {
            receiver = val;
            return this;
        }

        public Builder withStatus(NotificationStatus val) {
            status = val;
            return this;
        }

        public Builder withMessage(String val) {
            message = val;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
