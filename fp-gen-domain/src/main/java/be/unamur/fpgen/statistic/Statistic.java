package be.unamur.fpgen.statistic;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.dataset.AbstractDataset;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Statistic extends BaseUuidDomain {
    private final Integer total;
    private final BigDecimal fakeRatio;
    private final BigDecimal realRatio;
    private final BigDecimal socialEngineerRatio;
    private final BigDecimal trollRatio;
    private final Set<MessageTypeTopicStatistic> messageTypeTopicStatisticList = new HashSet<>();
    private final AbstractDataset dataset;

    private Statistic(Builder builder) {
        total = builder.total;
        fakeRatio = builder.fakeRatio;
        realRatio = builder.realRatio;
        socialEngineerRatio = builder.socialEngineerRatio;
        trollRatio = builder.trollRatio;
        messageTypeTopicStatisticList.addAll(builder.messageTypeTopicStatisticList);
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

    public BigDecimal getTrollRatio() {
        return trollRatio;
    }

    public Set<MessageTypeTopicStatistic> getMessageTopicStatisticList() {
        return messageTypeTopicStatisticList;
    }

    public AbstractDataset getDataset() {
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
        private BigDecimal trollRatio;
        private Set<MessageTypeTopicStatistic> messageTypeTopicStatisticList = new HashSet<>();
        private AbstractDataset dataset;

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

        public Builder withTrollRatio(BigDecimal val) {
            trollRatio = val;
            return this;
        }

        public Builder withMessageTopicStatisticList(Set<MessageTypeTopicStatistic> val) {
            messageTypeTopicStatisticList = val;
            return this;
        }

        public Builder withDataset(AbstractDataset val) {
            dataset = val;
            return this;
        }

        public Statistic build() {
            return new Statistic(this);
        }

        @Override
        protected Builder self() {
            return null;
        }
    }
}
