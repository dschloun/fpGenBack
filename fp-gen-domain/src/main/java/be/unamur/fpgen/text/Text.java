package be.unamur.fpgen.text;

import be.unamur.fpgen.BaseUuidDomain;

/**
 * This class could have been used to show some text to the front end such as general usage condition and so on.
 * It hasn't been used in the final version of the project.
 * We let it here for future work
 */
public class Text extends BaseUuidDomain {
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

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
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

        @Override
        protected Builder self() {
            return this;
        }
    }
}
