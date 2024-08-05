package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class GenerationQuery {
    private final GenerationTypeEnum generationType;
    private final MessageTypeEnum messageType;
    private final MessageTopicEnum messageTopic;
    private final String userPrompt;
    private final String systemPrompt;
    private final Integer quantity;
    private final String authorTrigram;
    private final List<UUID> datasetIdList;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    private GenerationQuery(Builder builder) {
        generationType = builder.generationType;
        messageType = builder.messageType;
        messageTopic = builder.messageTopic;
        userPrompt = builder.userPrompt;
        systemPrompt = builder.systemPrompt;
        quantity = builder.quantity;
        authorTrigram = builder.authorTrigram;
        datasetIdList = builder.datasetIdList;
        startDate = builder.startDate;
        endDate = builder.endDate;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public GenerationTypeEnum getGenerationType() {
        return generationType;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getAuthorTrigram() {
        return authorTrigram;
    }

    public List<UUID> getDatasetIdList() {
        return datasetIdList;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public static final class Builder {
        private GenerationTypeEnum generationType;
        private MessageTypeEnum messageType;
        private MessageTopicEnum messageTopic;
        private String userPrompt;
        private String systemPrompt;
        private Integer quantity;
        private String authorTrigram;
        private List<UUID> datasetIdList;
        private OffsetDateTime startDate;
        private OffsetDateTime endDate;

        private Builder() {
        }

        public Builder withGenerationType(GenerationTypeEnum val) {
            generationType = val;
            return this;
        }

        public Builder withMessageType(MessageTypeEnum val) {
            messageType = val;
            return this;
        }

        public Builder withMessageTopic(MessageTopicEnum val) {
            messageTopic = val;
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

        public Builder withQuantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder withAuthorTrigram(String val) {
            authorTrigram = val;
            return this;
        }

        public Builder withDatasetIdList(List<UUID> val) {
            datasetIdList = val;
            return this;
        }

        public Builder withStartDate(OffsetDateTime val) {
            startDate = val;
            return this;
        }

        public Builder withEndDate(OffsetDateTime val) {
            endDate = val;
            return this;
        }

        public GenerationQuery build() {
            return new GenerationQuery(this);
        }
    }
}
