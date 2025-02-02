package be.unamur.fpgen.interlocutor;

import be.unamur.fpgen.BaseOnlyIntegerId;

/**
 * Represents an interlocutor of a conversation
 */
public class Interlocutor extends BaseOnlyIntegerId {
    private final InterlocutorTypeEnum type;
    private final Integer number;

    private Interlocutor(Builder builder) {
        super(builder.getId());
        type = builder.type;
        number = builder.number;
    }

    public InterlocutorTypeEnum getType() {
        return type;
    }

    public Integer getNumber() {
        return number;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseOnlyIntegerIdBuilder<Builder>{
        private InterlocutorTypeEnum type;
        private Integer number;

        private Builder() {
        }

        public Builder withType(InterlocutorTypeEnum val) {
            type = val;
            return this;
        }

        public Builder withNumber(Integer val) {
            number = val;
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
