package be.unamur.fpgen.prompt;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.util.Objects;

/**
 * A prompt is dedicated to a dataset type(CONVERSATION / MESSAGE) and a message type (SOCIAL_ENGINEERING / HARASSMENT / GENUINE)
 * A version allow to track the evolution of the prompt
 * It's composed of a user prompt and a system prompt
 * it has a status because it has to be validated by the administrator
 */
public class Prompt extends BaseUuidDomain {
    private final DatasetTypeEnum datasetType;
    private final MessageTypeEnum messageType;
    private final Integer version;
    private String userPrompt;
    private String systemPrompt;
    private final Author author;
    private final PromptStatusEnum status;
    private final boolean defaultPrompt;
    private final String motivation;

    private Prompt(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        datasetType = builder.datasetType;
        messageType = builder.messageType;
        version = builder.version;
        userPrompt = builder.userPrompt;
        systemPrompt = builder.systemPrompt;
        author = builder.author;
        status = builder.status;
        defaultPrompt = builder.defaultPrompt;
        motivation = builder.motivation;
    }

    public DatasetTypeEnum getDatasetType() {
        return datasetType;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
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

    public boolean isDefaultPrompt() {
        return defaultPrompt;
    }

    public String getMotivation() {
        return motivation;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void updateSystemPrompt(final String content){
        this.systemPrompt = content;
    }

    public void updateUserPrompt(final String content){
        this.userPrompt = content;
    }

    // method to replace Placeholder with actual value in userPrompt
    public String replacePlaceholder(final Integer number, final Integer minInteractionNumber, final Integer maxInteractionNumber, final MessageTopicEnum topic){
        String output = this.userPrompt;
        if(Objects.nonNull(number)){
            output = this.userPrompt.replace(Placeholder.NUMBER.name(), number.toString());
        }
        if (Objects.nonNull(minInteractionNumber)){
            output = output.replace(Placeholder.MIN_INTERACTION.name(), minInteractionNumber.toString());
        }
        if (Objects.nonNull(maxInteractionNumber)){
            output = output.replace(Placeholder.MAX_INTERACTION.name(), maxInteractionNumber.toString());
        }
        if (Objects.nonNull(topic)){
            output = output.replace(Placeholder.TOPIC.name(), topic.name());
        }
        return output;
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private DatasetTypeEnum datasetType;
        private MessageTypeEnum messageType;
        private Integer version;
        private String userPrompt;
        private String systemPrompt;
        private Author author;
        private PromptStatusEnum status;
        private boolean defaultPrompt;
        private String motivation;

        private Builder() {
        }

        public Builder withDatasetType(DatasetTypeEnum val) {
            datasetType = val;
            return this;
        }

        public Builder withMessageType(MessageTypeEnum val) {
            messageType = val;
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

        public Builder withDefaultPrompt(boolean val) {
            defaultPrompt = val;
            return this;
        }

        public Builder withMotivation(String val) {
            motivation = val;
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
