package be.unamur.fpgen.message.download;

public class InstantMessageDownload {
    private final String messageId;
    private final String type;
    private final String topic;
    private final String content;
    private final boolean malicious;

    private InstantMessageDownload(Builder builder) {
        messageId = builder.messageId;
        type = builder.type;
        content = builder.content;
        topic = builder.topic;
        malicious = builder.malicious;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getType() {
        return type;
    }

    public String getTopic() {
        return topic;
    }

    public boolean isMalicious() {
        return malicious;
    }

    public String getContent() {
        return content;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String messageId;
        private String type;
        private String content;
        private String topic;
        private boolean malicious;

        private Builder() {
        }

        public Builder withMessageId(String val) {
            messageId = val;
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

        public Builder withTopic(String val) {
            topic = val;
            return this;
        }

        public InstantMessageDownload build() {
            return new InstantMessageDownload(this);
        }
    }
}
