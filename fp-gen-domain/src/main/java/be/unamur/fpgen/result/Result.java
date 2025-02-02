package be.unamur.fpgen.result;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.HasAuthor;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.Dataset;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represent a result obtain by a ML algorithm on a given dataset.
 */
public class Result extends BaseUuidDomain implements HasAuthor {
    private final Dataset dataset;
    private final Author author;
    private final OffsetDateTime experimentDate;
    private final String machineDetails;
    private final String algorithmType;
    private final Set<AlgorithmSetting> algorithmSettingList = new HashSet<>();
    private final String otherSettingDetails;
    private final BigDecimal accuracy;
    private final BigDecimal precision;
    private final BigDecimal recall;
    private final BigDecimal f1Score;
    private final BigDecimal prAuc;
    private final BigDecimal fpRate;
    private final BigDecimal fnRate;
    private final BigDecimal tpRate;
    private final BigDecimal tnRate;
    private final String appreciation;
    private final String comment;

    private Result(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        dataset = builder.dataset;
        author = builder.author;
        experimentDate = builder.experimentDate;
        machineDetails = builder.machineDetails;
        algorithmType = builder.algorithmType;
        algorithmSettingList.addAll(builder.algorithmSettingList);
        otherSettingDetails = builder.otherSettingDetails;
        accuracy = builder.accuracy;
        precision = builder.precision;
        recall = builder.recall;
        f1Score = builder.f1Score;
        prAuc = builder.prAuc;
        fpRate = builder.fpRate;
        fnRate = builder.fnRate;
        tpRate = builder.tpRate;
        tnRate = builder.tnRate;
        appreciation = builder.appreciation;
        comment = builder.comment;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public Author getAuthor() {
        return author;
    }

    public OffsetDateTime getExperimentDate() {
        return experimentDate;
    }

    public String getMachineDetails() {
        return machineDetails;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public Set<AlgorithmSetting> getAlgorithmSettingList() {
        return algorithmSettingList;
    }

    public String getOtherSettingDetails() {
        return otherSettingDetails;
    }

    public BigDecimal getAccuracy() {
        return accuracy;
    }

    public BigDecimal getPrecision() {
        return precision;
    }

    public BigDecimal getRecall() {
        return recall;
    }

    public BigDecimal getF1Score() {
        return f1Score;
    }

    public BigDecimal getPrAuc() {
        return prAuc;
    }

    public BigDecimal getFpRate() {
        return fpRate;
    }

    public BigDecimal getFnRate() {
        return fnRate;
    }

    public BigDecimal getTpRate() {
        return tpRate;
    }

    public BigDecimal getTnRate() {
        return tnRate;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public String getComment() {
        return comment;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder> {
        private Dataset dataset;
        private Author author;
        private OffsetDateTime experimentDate;
        private String machineDetails;
        private String algorithmType;
        private Set<AlgorithmSetting> algorithmSettingList = new HashSet<>();
        private String otherSettingDetails;
        private BigDecimal accuracy;
        private BigDecimal precision;
        private BigDecimal recall;
        private BigDecimal f1Score;
        private BigDecimal prAuc;
        private BigDecimal fpRate;
        private BigDecimal fnRate;
        private BigDecimal tpRate;
        private BigDecimal tnRate;
        private String appreciation;
        private String comment;

        private Builder() {
        }

        public Builder withDataset(Dataset val) {
            dataset = val;
            return this;
        }

        public Builder withAuthor(Author val) {
            author = val;
            return this;
        }

        public Builder withExperimentDate(OffsetDateTime val) {
            experimentDate = val;
            return this;
        }

        public Builder withMachineDetails(String val) {
            machineDetails = val;
            return this;
        }

        public Builder withAlgorithmType(String val) {
            algorithmType = val;
            return this;
        }

        public Builder withAlgorithmSettingList(Set<AlgorithmSetting> val) {
            algorithmSettingList = val;
            return this;
        }

        public Builder withOtherSettingDetails(String val) {
            otherSettingDetails = val;
            return this;
        }

        public Builder withAccuracy(BigDecimal val) {
            accuracy = val;
            return this;
        }

        public Builder withPrecision(BigDecimal val) {
            precision = val;
            return this;
        }

        public Builder withRecall(BigDecimal val) {
            recall = val;
            return this;
        }

        public Builder withF1Score(BigDecimal val) {
            f1Score = val;
            return this;
        }

        public Builder withPrAuc(BigDecimal val) {
            prAuc = val;
            return this;
        }

        public Builder withFpRate(BigDecimal val) {
            fpRate = val;
            return this;
        }

        public Builder withFnRate(BigDecimal val) {
            fnRate = val;
            return this;
        }

        public Builder withTpRate(BigDecimal val) {
            tpRate = val;
            return this;
        }

        public Builder withTnRate(BigDecimal val) {
            tnRate = val;
            return this;
        }

        public Builder withAppreciation(String val) {
            appreciation = val;
            return this;
        }

        public Builder withComment(String val) {
            comment = val;
            return this;
        }

        public Result build() {
            return new Result(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
