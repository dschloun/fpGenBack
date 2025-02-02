package be.unamur.fpgen.result;

import be.unamur.fpgen.BaseUuidDomain;

/**
 * This class represents a setting for an algorithm.
 * It is composed of a parameter name and a value.
 * It's used in the result to indicate what were the settings used by the model
 */
public class AlgorithmSetting extends BaseUuidDomain {
    private final String parameterName;
    private final String value;

    private AlgorithmSetting(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        parameterName = builder.parameterName;
        value = builder.value;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getValue() {
        return value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private String parameterName;
        private String value;

        private Builder() {
        }

        public Builder withParameterName(String val) {
            parameterName = val;
            return this;
        }

        public Builder withValue(String val) {
            value = val;
            return this;
        }

        public AlgorithmSetting build() {
            return new AlgorithmSetting(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
