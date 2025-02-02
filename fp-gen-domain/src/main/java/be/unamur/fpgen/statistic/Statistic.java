package be.unamur.fpgen.statistic;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.dataset.Dataset;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Represent the descriptive statistics of a dataset.
 * actually it's more the characteristics of the dataset
 * it shows the distributions between real and fake profile with different metrics
 */
public class Statistic extends BaseUuidDomain {
    private final Integer total;
    private final BigDecimal fakeRatio;
    private final BigDecimal realRatio;
    private final BigDecimal socialEngineerRatio;
    private final BigDecimal harasserRatio;
    private final Set<MessageTypeTopicStatistic> genuineTopicStatisticList = new HashSet<>();
    private final Set<MessageTypeTopicStatistic> socialEngineeringTopicStatisticList = new HashSet<>();
    private final Set<MessageTypeTopicStatistic> harassmentTopicStatisticList = new HashSet<>();
    private final Dataset dataset;

    private Statistic(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        total = builder.total;
        fakeRatio = builder.fakeRatio;
        realRatio = builder.realRatio;
        socialEngineerRatio = builder.socialEngineerRatio;
        harasserRatio = builder.harasserRatio;
        genuineTopicStatisticList.addAll(builder.genuineTopicStatisticList);
        socialEngineeringTopicStatisticList.addAll(builder.socialEngineeringTopicStatisticList);
        harassmentTopicStatisticList.addAll(builder.harassmentTopicStatisticList);
        dataset = builder.dataset;
    }

    public Integer getTotal() {
        return total;
    }

    public BigDecimal getFakeRatio() {
        return fakeRatio;
    }

    public BigDecimal getRealRatio() {
        return realRatio;
    }

    public BigDecimal getSocialEngineerRatio() {
        return socialEngineerRatio;
    }

    public BigDecimal getHarasserRatio() {
        return harasserRatio;
    }

    public Set<MessageTypeTopicStatistic> getGenuineTopicStatisticList() {
        return genuineTopicStatisticList;
    }

    public Set<MessageTypeTopicStatistic> getSocialEngineeringTopicStatisticList() {
        return socialEngineeringTopicStatisticList;
    }

    public Set<MessageTypeTopicStatistic> getHarassmentTopicStatisticList() {
        return harassmentTopicStatisticList;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private Integer total;
        private BigDecimal fakeRatio;
        private BigDecimal realRatio;
        private BigDecimal socialEngineerRatio;
        private BigDecimal harasserRatio;
        private Set<MessageTypeTopicStatistic> genuineTopicStatisticList = new HashSet<>();
        private Set<MessageTypeTopicStatistic> socialEngineeringTopicStatisticList = new HashSet<>();
        private Set<MessageTypeTopicStatistic> harassmentTopicStatisticList = new HashSet<>();
        private Dataset dataset;

        private Builder() {
        }

        public Builder withTotal(Integer val) {
            total = val;
            return this;
        }

        public Builder withFakeRatio(BigDecimal val) {
            fakeRatio = val;
            return this;
        }

        public Builder withRealRatio(BigDecimal val) {
            realRatio = val;
            return this;
        }

        public Builder withSocialEngineerRatio(BigDecimal val) {
            socialEngineerRatio = val;
            return this;
        }

        public Builder withHarasserRatio(BigDecimal val) {
            harasserRatio = val;
            return this;
        }

        public Builder withGenuineTopicStatisticList(Set<MessageTypeTopicStatistic> val) {
            genuineTopicStatisticList = val;
            return this;
        }

        public Builder withSocialEngineeringTopicStatisticList(Set<MessageTypeTopicStatistic> val) {
            socialEngineeringTopicStatisticList = val;
            return this;
        }

        public Builder withHarassmentTopicStatisticList(Set<MessageTypeTopicStatistic> val) {
            harassmentTopicStatisticList = val;
            return this;
        }

        public Builder withDataset(Dataset val) {
            dataset = val;
            return this;
        }

        public Statistic build() {
            return new Statistic(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
