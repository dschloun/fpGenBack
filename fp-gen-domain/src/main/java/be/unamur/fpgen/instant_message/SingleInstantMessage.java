package be.unamur.fpgen.instant_message;

public class SingleInstantMessage extends AbstractInstantMessage {

    // constructors
    public SingleInstantMessage(final Builder builder) {
        super(builder.getId(),
                builder.getCreationDate(),
                builder.getModificationDate(),
                builder.getTopic(),
                builder.getType(),
                builder.getContent());
    }

    // builder
    public static final class Builder extends AbstractInstantMessageBuilder<Builder> {

        Builder() {
        }

        public SingleInstantMessage build() {
            return new SingleInstantMessage(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}