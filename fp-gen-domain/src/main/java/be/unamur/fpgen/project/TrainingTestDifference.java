package be.unamur.fpgen.project;

import java.util.Set;

public class TrainingTestDifference {
    private final Integer fakeDifference;
    private final Integer realDifference;
    private final Integer socialEngineeringDifference;
    private final Integer trollingDifference;
    private final Set<TypeTopicDifference> typeTopicDifferences;

    private TrainingTestDifference(Builder builder) {
        fakeDifference = builder.fakeDifference;
        realDifference = builder.realDifference;
        socialEngineeringDifference = builder.socialEngineeringDifference;
        trollingDifference = builder.trollingDifference;
        typeTopicDifferences = builder.typeTopicDifferences;
    }

    public Integer getFakeDifference() {
        return fakeDifference;
    }

    public Integer getRealDifference() {
        return realDifference;
    }

    public Integer getSocialEngineeringDifference() {
        return socialEngineeringDifference;
    }

    public Integer getTrollingDifference() {
        return trollingDifference;
    }

    public Set<TypeTopicDifference> getTypeTopicDifferences() {
        return typeTopicDifferences;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer fakeDifference;
        private Integer realDifference;
        private Integer socialEngineeringDifference;
        private Integer trollingDifference;
        private Set<TypeTopicDifference> typeTopicDifferences;

        private Builder() {
        }

        public Builder withFakeDifference(Integer val) {
            fakeDifference = val;
            return this;
        }

        public Builder withRealDifference(Integer val) {
            realDifference = val;
            return this;
        }

        public Builder withSocialEngineeringDifference(Integer val) {
            socialEngineeringDifference = val;
            return this;
        }

        public Builder withTrollingDifference(Integer val) {
            trollingDifference = val;
            return this;
        }

        public Builder withTypeTopicDifferences(Set<TypeTopicDifference> val) {
            typeTopicDifferences = val;
            return this;
        }

        public TrainingTestDifference build() {
            return new TrainingTestDifference(this);
        }
    }
}
