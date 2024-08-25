package be.unamur.fpgen.message.download;

public class InstantMessageDownload {
    private final String type;
    private final String content;

    private InstantMessageDownload(Builder builder) {
        type = builder.type;
        content = builder.content;
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
        private String type;
        private String content;

        private Builder() {
        }

        public Builder withType(String val) {
            type = val;
            return this;
        }

        public Builder withContent(String val) {
            content = val;
            return this;
        }

        public InstantMessageDownload build() {
            return new InstantMessageDownload(this);
        }
    }
}
