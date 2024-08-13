package be.unamur.fpgen.dataset;

import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

public class ConversationDataset extends AbstractDataset {
    private final Set<ConversationGeneration> conversationGenerationList = new HashSet<>();

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
                builder.getAuthor(),
                builder.getDatasetFunction(),
                builder.getOngoingGenerationId(),
                builder.getStatistic());
        this.conversationGenerationList.addAll(builder.conversationGenerationList);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractDatasetBuilder<Builder>{
        private Set<ConversationGeneration> conversationGenerationList = new HashSet<>();

        private Builder() {
        }

        public Builder withConversationGenerationList(Set<ConversationGeneration> val) {
            conversationGenerationList = val;
            return this;
        }

        public ConversationDataset build() {
            businessId = generateGenerationId();
            return new ConversationDataset(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public String generateGenerationId() {
            return String.format("CDS-%s-%s-%s", this.getDatasetFunction(), this.getAuthor().getTrigram(), DateUtil.convertOffsetDateTimeToString(OffsetDateTime.now()));
        }
    }
}
