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
    private final Integer promptVersion;
    private final Integer quantity;
    private final String authorTrigram;
    private final List<UUID> notInDatasetIdList;
    private final List<UUID> inDatasetIdList;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    private GenerationQuery(Builder builder) {
        generationType = builder.generationType;
        messageType = builder.messageType;
        messageTopic = builder.messageTopic;
        promptVersion = builder.promptVersion;
        quantity = builder.quantity;
        authorTrigram = builder.authorTrigram;
        notInDatasetIdList = builder.notInDatasetIdList;
        inDatasetIdList = builder.inDatasetIdList;
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

    public Integer getPromptVersion() {
        return promptVersion;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getAuthorTrigram() {
        return authorTrigram;
    }

    public List<UUID> getNotInDatasetIdList() {
        return notInDatasetIdList;
    }

    public List<UUID> getInDatasetIdList() {
        return inDatasetIdList;
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
        private Integer promptVersion;
        private Integer quantity;
        private String authorTrigram;
        private List<UUID> notInDatasetIdList;
        private List<UUID> inDatasetIdList;
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

        public Builder withPromptVersion(Integer val) {
            promptVersion = val;
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

        public Builder withNotInDatasetIdList(List<UUID> val) {
            notInDatasetIdList = val;
            return this;
        }

        public Builder withInDatasetIdList(List<UUID> val) {
            inDatasetIdList = val;
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
