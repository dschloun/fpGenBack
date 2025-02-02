package be.unamur.fpgen.project;

import java.util.Set;

/**
 * This class represents the differences between the training and test sets.
 * It contains the differences for the fake, real, social engineering and harassment topics.
 * It also contains the differences for each type of topic.
 */
public class TrainingTestDifference {
    private final Integer fakeDifference;
    private final Integer realDifference;
    private final Integer socialEngineeringDifference;
    private final Integer harassmentDifference;
    private final Set<TypeTopicDifference> typeTopicDifferences;

    private TrainingTestDifference(Builder builder) {
        fakeDifference = builder.fakeDifference;
        realDifference = builder.realDifference;
        socialEngineeringDifference = builder.socialEngineeringDifference;
        harassmentDifference = builder.harassmentDifference;
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

    public Integer getHarassmentDifference() {
        return harassmentDifference;
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
        private Integer harassmentDifference;
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

        public Builder withHarassmentDifference(Integer val) {
            harassmentDifference = val;
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
