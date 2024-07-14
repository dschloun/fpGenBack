package be.unamur.fpgen.dataset;

import be.unamur.fpgen.generation.InstantMessageGeneration;

import java.util.List;

public class InstantMessageDataset extends AbstractDataset{

    private final List<InstantMessageGeneration> instantMessageGenerationList;

    private InstantMessageDataset(Builder builder) {
        super(builder.getId(),
                builder.getCreationDate(),
                builder.getModificationDate(),
                builder.getBusinessId(),
                builder.getVersion(),
                builder.getName(),
                builder.getDescription(),
                builder.getComment(),
                builder.getAuthor());
        instantMessageGenerationList = builder.instantMessageGenerationList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<InstantMessageGeneration> getInstantMessageGenerationList() {
        return instantMessageGenerationList;
    }

    public static final class Builder extends AbstractDatasetBuilder<Builder>{

        private List<InstantMessageGeneration> instantMessageGenerationList;

        private Builder() {
        }

        public Builder withInstantMessageGenerationList(List<InstantMessageGeneration> val) {
            instantMessageGenerationList = val;
            return this;
        }

        public InstantMessageDataset build() {
            return new InstantMessageDataset(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
