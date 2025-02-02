package be.unamur.fpgen.generation.ongoing_generation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.util.UUID;

/**
 * Represents an ongoing generation item of a dataset.
 * It's an item of the generation request, which has not been generated yet
 */
public class OngoingGenerationItem extends BaseUuidDomain {

    private final MessageTypeEnum messageType;
    private final MessageTopicEnum messageTopic;
    private Integer quantity;
    private OngoingGenerationItemStatus status;
    private final UUID generationId;
    private final UUID promptId;

    private OngoingGenerationItem(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        messageType = builder.messageType;
        messageTopic = builder.messageTopic;
        quantity = builder.quantity;
        status = builder.status;
        generationId = builder.generationId;
        promptId = builder.promptId;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void decrementQuantity(){
        this.quantity--;
    }

    public OngoingGenerationItemStatus getStatus() {
        return status;
    }

    public void updateStatus(OngoingGenerationItemStatus status) {
        this.status = status;
    }

    public UUID getGenerationId() {
        return generationId;
    }

    public UUID getPromptId() {
        return promptId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder> {
        private MessageTypeEnum messageType;
        private MessageTopicEnum messageTopic;
        private Integer quantity;
        private OngoingGenerationItemStatus status;
        private UUID generationId;
        private UUID promptId;

        private Builder() {
        }


        public Builder withMessageType(MessageTypeEnum val) {
            messageType = val;
            return this;
        }

        public Builder withMessageTopic(MessageTopicEnum val) {
            messageTopic = val;
            return this;
        }

        public Builder withQuantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder withStatus(OngoingGenerationItemStatus val) {
            status = val;
            return this;
        }

        public Builder withGenerationId(UUID val) {
            generationId = val;
            return this;
        }

        public Builder withPromptId(UUID promptId){
            this.promptId = promptId;
            return this;
        }

        public OngoingGenerationItem build() {
            return new OngoingGenerationItem(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
