package be.unamur.fpgen;

public class Text extends BaseUuidDomain{
    private final String title;
    private final String content;

    private Text(Builder builder) {
        title = builder.title;
        content = builder.content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String title;
        private String content;

        private Builder() {
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withContent(String val) {
            content = val;
            return this;
        }

        public Text build() {
            return new Text(this);
        }
    }
}
