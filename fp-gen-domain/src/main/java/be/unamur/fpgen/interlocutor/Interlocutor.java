package be.unamur.fpgen.interlocutor;

import be.unamur.fpgen.BaseOnlyIntegerId;

public class Interlocutor extends BaseOnlyIntegerId {
    private final InterlocutorTypeEnum type;

    private Interlocutor(Builder builder) {
        super(builder.getId());
        type = builder.type;
    }

    public InterlocutorTypeEnum getType() {
        return type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseOnlyIntegerIdBuilder<Builder>{
        private InterlocutorTypeEnum type;

        private Builder() {
        }

        public Builder withType(InterlocutorTypeEnum val) {
            type = val;
            return this;
        }

        public Interlocutor build() {
            return new Interlocutor(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
