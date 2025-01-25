package be.unamur.fpgen.message.download;

public class ConversationMessageDownload {
    private final String conversationId;
    private final String orderNumber;
    private final String type;
    private final String topic;
    private final String content;
    private final Integer senderId;
    private final Integer receiverId;
    private final boolean malicious;

    private ConversationMessageDownload(Builder builder) {
        conversationId = builder.conversationId;
        orderNumber = builder.orderNumber;
        type = builder.type;
        content = builder.content;
        senderId = builder.senderId;
        receiverId = builder.receiverId;
        malicious = builder.malicious;
        topic = builder.topic;
    }

    public String getTopic() {
        return topic;
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

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public boolean isMalicious() {
        return malicious;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String conversationId;
        private String orderNumber;
        private String type;
        private String content;
        private Integer senderId;
        private Integer receiverId;
        private boolean malicious;
        private String topic;

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
            malicious = val.equals("SOCIAL_ENGINEERING") || val.equals("HARASSMENT");
            return this;
        }

        public Builder withContent(String val) {
            content = val;
            return this;
        }

        public Builder withSenderId(Integer val) {
            senderId = val;
            return this;
        }

        public Builder withReceiverId(Integer val) {
            receiverId = val;
            return this;
        }

        public Builder withTopic(String val) {
            topic = val;
            return this;
        }

        public ConversationMessageDownload build() {
            return new ConversationMessageDownload(this);
        }
    }
}
