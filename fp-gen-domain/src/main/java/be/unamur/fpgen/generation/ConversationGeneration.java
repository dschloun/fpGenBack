package be.unamur.fpgen.generation;

import be.unamur.fpgen.instant_message.ConversationMessage;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;
import java.util.List;

public class ConversationGeneration extends AbstractGeneration{
    private final List<ConversationMessage> conversationMessageList;

    public List<ConversationMessage> getConversationMessageList() {
        return conversationMessageList;
    }

    private ConversationGeneration(Builder builder) {
        super(builder.getId(),
                builder.getCreationDate(),
                builder.getModificationDate(),
                builder.getAuthor(),
                builder.getDetails(),
                builder.isBatch());
        conversationMessageList = builder.conversationMessageList;
        generationId = builder.getGenerationId();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractGenerationBuilder<Builder>{
        private List<ConversationMessage> conversationMessageList;

        private Builder() {
        }

        public Builder withConversationMessageList(List<ConversationMessage> val) {
            conversationMessageList = val;
            return this;
        }

        public ConversationGeneration build() {
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
