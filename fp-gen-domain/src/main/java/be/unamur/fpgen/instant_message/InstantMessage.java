package be.unamur.fpgen.instant_message;

public class InstantMessage extends AbstractInstantMessage {

    // constructors
    public InstantMessage(final Builder builder) {
        super(builder.getId(),
                builder.getCreationDate(),
                builder.getModificationDate(),
                builder.getTopic(),
                builder.getType(),
                builder.getContent(),
                builder.getGeneration(),
                builder.isBatch());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    // builder
    public static final class Builder extends AbstractInstantMessageBuilder<Builder> {

        Builder() {
        }

        public InstantMessage build() {
            return new InstantMessage(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}