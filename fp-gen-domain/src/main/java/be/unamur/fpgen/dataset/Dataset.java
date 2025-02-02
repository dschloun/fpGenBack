package be.unamur.fpgen.dataset;

import be.unamur.fpgen.AbstractItem;
import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.HasAuthor;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a dataset
 * a dataset as a type (Message or Conversation)
 * a function (TRAINING, TEST, VALIDATION)
 * a descriptive statistic which represent the distribution, size, ...
 */
public class Dataset extends BaseUuidDomain implements HasAuthor {
    private final DatasetTypeEnum type;
    private final String businessId;
    private final Integer version;
    private final String name;
    private final String description;
    private final String comment;
    private final Author author;
    private final DatasetFunctionEnum datasetFunction;
    private final UUID ongoingGenerationId;
    private final Statistic statistic;
    private boolean validated;
    private boolean lastVersion;
    private final UUID originalDatasetId;
    private final boolean result;
    private final Set<AbstractItem> itemList = new HashSet<>();
    private final Integer recordNumber;

    private Dataset(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        type = builder.type;
        businessId = builder.businessId;
        version = builder.version;
        name = builder.name;
        description = builder.description;
        comment = builder.comment;
        author = builder.author;
        datasetFunction = builder.datasetFunction;
        ongoingGenerationId = builder.ongoingGenerationId;
        statistic = builder.statistic;
        validated = builder.validated;
        lastVersion = builder.lastVersion;
        originalDatasetId = builder.originalDatasetId;
        result = builder.result;
        itemList.addAll(builder.itemList);
        recordNumber = builder.recordNumber;
    }

    public DatasetTypeEnum getType() {
        return type;
    }

    public String getBusinessId() {
        return businessId;
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }

    public Author getAuthor() {
        return author;
    }

    public DatasetFunctionEnum getDatasetFunction() {
        return datasetFunction;
    }

    public UUID getOngoingGenerationId() {
        return ongoingGenerationId;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public boolean isValidated() {
        return validated;
    }

    public void validateDataset() {
        this.validated = true;
    }

    public boolean isLastVersion() {
        return lastVersion;
    }

    public void isNotLastVersionAnymore(){
        this.lastVersion = false;
    }

    public void isLastVersionAgain(){
        this.lastVersion = true;
    }

    public UUID getOriginalDatasetId() {
        return originalDatasetId;
    }

    public boolean hasResult() {
        return result;
    }

    public Set<AbstractItem> getItemList() {
        return itemList;
    }

    public Integer getRecordNumber() {
        return recordNumber;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder> {
        private DatasetTypeEnum type;
        private String businessId;
        private Integer version;
        private String name;
        private String description;
        private String comment;
        private Author author;
        private DatasetFunctionEnum datasetFunction;
        private UUID ongoingGenerationId;
        private Statistic statistic;
        private boolean validated;
        private boolean lastVersion;
        private UUID originalDatasetId;
        private boolean result;
        private Set<AbstractItem> itemList = new HashSet<>();
        private Integer recordNumber;

        private Builder() {
        }

        public Builder withType(DatasetTypeEnum val) {
            type = val;
            return this;
        }

        public Builder withBusinessId(String val) {
            businessId = val;
            return this;
        }

        public Builder withVersion(Integer val) {
            version = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withDescription(String val) {
            description = val;
            return this;
        }

        public Builder withComment(String val) {
            comment = val;
            return this;
        }

        public Builder withAuthor(Author val) {
            author = val;
            return this;
        }

        public Builder withDatasetFunction(DatasetFunctionEnum val) {
            datasetFunction = val;
            return this;
        }

        public Builder withOngoingGenerationId(UUID val) {
            ongoingGenerationId = val;
            return this;
        }

        public Builder withStatistic(Statistic val) {
            statistic = val;
            return this;
        }

        public Builder withValidated(boolean val) {
            validated = val;
            return this;
        }

        public Builder withLastVersion(boolean val) {
            lastVersion = val;
            return this;
        }

        public Builder withOriginalDatasetId(UUID val) {
            originalDatasetId = val;
            return this;
        }

        public Builder withResult(boolean val) {
            result = val;
            return this;
        }

        public Builder withItemList(Set<AbstractItem> val) {
            itemList = val;
            return this;
        }

        public Builder withRecordNumber(Integer recordNumber){
            this.recordNumber = recordNumber;
            return this;
        }

        public Dataset build() {
            businessId = generateGenerationId();
            return new Dataset(this);
        }

        /**
         * Generate a unique identifier for the dataset
         * @return the generated identifier
         */
        public String generateGenerationId() {
            return String.format("%s-%s-%s-%s", this.type, this.datasetFunction, this.author.getTrigram(), DateUtil.convertOffsetDateTimeToString(OffsetDateTime.now()));
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
