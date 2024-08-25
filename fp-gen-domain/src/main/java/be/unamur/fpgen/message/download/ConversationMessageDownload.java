package be.unamur.fpgen.message.download;

public class ConversationMessageDownload {
    private final String conversationId;
    private final String orderNumber;
    private final String type;
    private final String content;

    private ConversationMessageDownload(Builder builder) {
        conversationId = builder.conversationId;
        orderNumber = builder.orderNumber;
        type = builder.type;
        content = builder.content;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String conversationId;
        private String orderNumber;
        private String type;
        private String content;

        private Builder() {
        }

        public Builder withConversationId(String val) {
            conversationId = val;
            return this;
        }

        public Builder withOrderNumber(String val) {
            orderNumber = val;
            return this;
        }

        public Builder withType(String val) {
            type = val;
            return this;
        }

        public Builder withContent(String val) {
            content = val;
            return this;
        }

        public ConversationMessageDownload build() {
            return new ConversationMessageDownload(this);
        }
    }
}
