package be.unamur.fpgen.dataset;

import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

public class InstantMessageDataset extends AbstractDataset {

    private final Set<InstantMessageGeneration> instantMessageGenerationList = new HashSet<>();

    private InstantMessageDataset(Builder builder) {
        super(builder.getId(),
                builder.getCreationDate(),
                builder.getModificationDate(),
                builder.getBusinessId(),
                builder.getVersion(),
                builder.getName(),
                builder.getDescription(),
                builder.getComment(),
                builder.getAuthor(),
                builder.getDatasetFunction(),
                builder.getOngoingGenerationId());
        instantMessageGenerationList.addAll(builder.instantMessageGenerationList);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Set<InstantMessageGeneration> getInstantMessageGenerationList() {
        return instantMessageGenerationList;
    }

    public static final class Builder extends AbstractDatasetBuilder<Builder> {

        private Set<InstantMessageGeneration> instantMessageGenerationList = new HashSet<>();

        private Builder() {
        }

        public Builder withInstantMessageGenerationList(Set<InstantMessageGeneration> val) {
            instantMessageGenerationList = val;
            return this;
        }

        public InstantMessageDataset build() {
            businessId = generateGenerationId();
            return new InstantMessageDataset(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public String generateGenerationId() {
            return String.format("IMDS-%s-%s-%s", this.getDatasetFunction(), this.getAuthor().getTrigram(), DateUtil.convertOffsetDateTimeToString(OffsetDateTime.now()));
        }
    }
}
