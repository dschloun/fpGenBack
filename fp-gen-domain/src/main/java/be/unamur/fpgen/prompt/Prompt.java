package be.unamur.fpgen.prompt;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.message.MessageTypeEnum;

public class Prompt extends BaseUuidDomain {
    private final MessageTypeEnum type;
    private final Integer version;
    private final String userPrompt;
    private final String systemPrompt;
    private final Author author;
    private final PromptStatusEnum status;

    private Prompt(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        type = builder.type;
        version = builder.version;
        userPrompt = builder.userPrompt;
        systemPrompt = builder.systemPrompt;
        author = builder.author;
        status = builder.status;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public Integer getVersion() {
        return version;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public Author getAuthor() {
        return author;
    }

    public PromptStatusEnum getStatus() {
        return status;
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private MessageTypeEnum type;
        private Integer version;
        private String userPrompt;
        private String systemPrompt;
        private Author author;
        private PromptStatusEnum status;

        private Builder() {
        }

        public Builder withType(MessageTypeEnum val) {
            type = val;
            return this;
        }

        public Builder withVersion(Integer val) {
            version = val;
            return this;
        }

        public Builder withUserPrompt(String val) {
            userPrompt = val;
            return this;
        }

        public Builder withSystemPrompt(String val) {
            systemPrompt = val;
            return this;
        }

        public Builder withAuthor(Author val) {
            author = val;
            return this;
        }

        public Builder withStatus(PromptStatusEnum val) {
            status = val;
            return this;
        }

        public Prompt build() {
            return new Prompt(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
