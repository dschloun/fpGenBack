package be.unamur.fpgen.generation;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;
import java.util.Set;

public class ConversationGeneration extends AbstractGeneration{
    private final Set<Conversation> conversationList;

    public Set<Conversation> getConversationList() {
        return conversationList;
    }

    private ConversationGeneration(Builder builder) {
        super(builder.getId(),
                builder.getCreationDate(),
                builder.getModificationDate(),
                builder.getAuthor(),
                builder.getDetails(),
                builder.isBatch());
        conversationList = builder.conversationList;
        generationId = builder.getGenerationId();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractGenerationBuilder<Builder>{
        private Set<Conversation> conversationList;

        private Builder() {
        }

        public Builder withConversationList(Set<Conversation> val) {
            conversationList = val;
            return this;
        }

        public ConversationGeneration build() {
            generationId = generateGenerationId();
            return new ConversationGeneration(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public String generateGenerationId() {
            return String.format("%s-%s-%s-%s", "C", this.returnBatchOrSingle(), this.getAuthor().getTrigram(), DateUtil.convertOffsetDateTimeToString(OffsetDateTime.now()));
        }
    }
}
