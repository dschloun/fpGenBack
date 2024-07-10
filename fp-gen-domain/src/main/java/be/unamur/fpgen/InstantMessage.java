package be.unamur.fpgen;

public class InstantMessage extends BaseUuidDomain {
    // members
    private InstantMessageTopicEnum topic;
    private String content;

    // constructors
    public InstantMessage(final Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        this.topic = builder.topic;
        this.content = builder.content;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    // getters
    public InstantMessageTopicEnum getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    // builder
    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private InstantMessageTopicEnum topic;
        private String content;

        private Builder() {
        }

        public Builder withTopic(final InstantMessageTopicEnum topic) {
            this.topic = topic;
            return this;
        }

        public Builder withContent(final String content) {
            this.content = content;
            return this;
        }

        public InstantMessage build(){
            return new InstantMessage(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
