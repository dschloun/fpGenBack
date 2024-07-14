package be.unamur.fpgen.dataset;

import be.unamur.fpgen.instant_message.ConversationMessage;

import java.util.List;

public class ConversationDataset extends AbstractDataset {
    private final List<ConversationMessage> conversationMessageList;

    public List<ConversationMessage> getConversationMessageList() {
        return conversationMessageList;
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
        this.conversationMessageList = builder.conversationMessageList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractDatasetBuilder<Builder>{
        private List<ConversationMessage> conversationMessageList;

        private Builder() {
        }

        public Builder withConversationMessageList(List<ConversationMessage> val) {
            conversationMessageList = val;
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
