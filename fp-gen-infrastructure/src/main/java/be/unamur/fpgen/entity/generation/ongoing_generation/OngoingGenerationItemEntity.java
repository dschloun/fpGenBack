package be.unamur.fpgen.entity.generation.ongoing_generation;

import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItemStatus;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import javax.persistence.*;
import java.util.UUID;

/**
 * Represents an ongoing generation item of a dataset.
 * It's an item of the generation request, which has not been generated yet
 */
@Entity
@Table(name = "ongoing_generation_item")
public class OngoingGenerationItemEntity extends BaseUuidEntity {
    private OngoingGenerationEntity ongoingGeneration;
    private MessageTypeEnum messageType;
    private MessageTopicEnum messageTopic;
    private Integer quantity;
    private OngoingGenerationItemStatus status;
    private UUID promptId;

    @ManyToOne
    @JoinColumn(name = "ongoing_generation_id")
    public OngoingGenerationEntity getOngoingGeneration() {
        return ongoingGeneration;
    }

    public void setOngoingGeneration(OngoingGenerationEntity ongoingGeneration) {
        this.ongoingGeneration = ongoingGeneration;
    }

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum type) {
        this.messageType = type;
    }

    @Column(name = "message_topic", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public void setMessageTopic(MessageTopicEnum topic) {
        this.messageTopic = topic;
    }

    @Column(name = "quantity", nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public OngoingGenerationItemStatus getStatus() {
        return status;
    }

    public void setStatus(OngoingGenerationItemStatus status) {
        this.status = status;
    }

    @Column(name = "prompt_id")
    public UUID getPromptId() {
        return promptId;
    }

    public void setPromptId(UUID promptId) {
        this.promptId = promptId;
    }
}
