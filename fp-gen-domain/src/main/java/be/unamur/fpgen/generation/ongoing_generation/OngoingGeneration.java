package be.unamur.fpgen.generation.ongoing_generation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.generation.GenerationTypeEnum;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents an ongoing generation of a dataset.
 * It's a generation request, it has not been generated yet
 */
public class OngoingGeneration extends BaseUuidDomain {

    private final GenerationTypeEnum type;
    private final Set<OngoingGenerationItem> itemList = new HashSet<>();
    private final Author author;
    private OngoingGenerationStatus status;
    private final UUID datasetId;
    private final Integer minInteractionNumber;
    private final Integer maxInteractionNumber;

    private OngoingGeneration(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        type = builder.type;
        itemList.addAll(builder.itemList);
        author = builder.author;
        status = builder.status;
        datasetId = builder.datasetId;
        minInteractionNumber = builder.minInteractionNumber;
        maxInteractionNumber = builder.maxInteractionNumber;
    }

    public GenerationTypeEnum getType() {
        return type;
    }

    public Set<OngoingGenerationItem> getItemList() {
        return itemList;
    }

    public Author getAuthor() {
        return author;
    }

    public OngoingGenerationStatus getStatus() {
        return status;
    }

    public void updateStatus(OngoingGenerationStatus status) {
        this.status = status;
    }

    public UUID getDatasetId() {
        return datasetId;
    }

    public Integer getMinInteractionNumber() {
        return minInteractionNumber;
    }

    public Integer getMaxInteractionNumber() {
        return maxInteractionNumber;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{

        private GenerationTypeEnum type;
        private Set<OngoingGenerationItem> itemList = new HashSet<>();
        private Author author;
        private OngoingGenerationStatus status;
        private UUID datasetId;
        private Integer minInteractionNumber;
        private Integer maxInteractionNumber;

        private Builder() {
        }

        public Builder withType(GenerationTypeEnum val) {
            type = val;
            return this;
        }

        public Builder withItemList(Set<OngoingGenerationItem> val) {
            itemList = val;
            return this;
        }

        public Builder withAuthor(Author val) {
            author = val;
            return this;
        }

        public Builder withStatus(OngoingGenerationStatus val) {
            status = val;
            return this;
        }

        public Builder withDatasetId(UUID val){
            datasetId = val;
            return this;
        }

        public Builder withMinInteractionNumber(Integer val) {
            minInteractionNumber = val;
            return this;
        }

        public Builder withMaxInteractionNumber(Integer val) {
            maxInteractionNumber = val;
            return this;
        }

        public OngoingGeneration build() {
            return new OngoingGeneration(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
