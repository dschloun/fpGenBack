package be.unamur.fpgen.dataset;

import be.unamur.fpgen.generation.ConversationGeneration;

import java.util.Set;

public class ConversationDataset extends AbstractDataset {
    private final Set<ConversationGeneration> conversationGenerationList;

    public Set<ConversationGeneration> getConversationGenerationList() {
        return conversationGenerationList;
    }

    public ConversationDataset(Builder builder) {
        super(builder.getId(),
                builder.getModificationDate(),
                builder.getModificationDate(),
                builder.getBusinessId(),
                builder.getVersion(),
                builder.getName(),
                builder.getDescription(),
                builder.getComment(),
                builder.getAuthor());
        this.conversationGenerationList = builder.conversationGenerationList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractDatasetBuilder<Builder>{
        private Set<ConversationGeneration> conversationGenerationList;

        private Builder() {
        }

        public Builder withConversationGenerationList(Set<ConversationGeneration> val) {
            conversationGenerationList = val;
            return this;
        }

        public ConversationDataset build() {
            return new ConversationDataset(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
