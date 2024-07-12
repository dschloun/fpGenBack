package be.unamur.fpgen.entity.conversation;

import be.unamur.fpgen.entity.BaseUuidEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "conversation")
public class ConversationEntity extends BaseUuidEntity {

    // members
    private ConversationGenerationEntity conversationGeneration;
    private MessageTypeEnum messageType;
    private MessageTopicEnum messageTopic;
    private Integer maxInteractionNumber;
    private Set<ConversationInstantMessageEntity> messageSet;

    // getters and setters
    @ManyToOne
    @JoinColumn(name = "conversation_generation_id")
    public ConversationGenerationEntity getConversationGeneration() {
        return conversationGeneration;
    }

    public void setConversationGeneration(ConversationGenerationEntity conversationGeneration) {
        this.conversationGeneration = conversationGeneration;
    }

    @Column(name = "type", nullable = false)
    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    @Column(name = "topic", nullable = false)
    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public void setMessageTopic(MessageTopicEnum messageTopic) {
        this.messageTopic = messageTopic;
    }

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<ConversationInstantMessageEntity> getMessageSet() {
        return messageSet;
    }

    public void setMessageSet(Set<ConversationInstantMessageEntity> messageSet) {
        this.messageSet = messageSet;
    }

    @Column(name = "max_interaction_number")
    public Integer getMaxInteractionNumber() {
        return maxInteractionNumber;
    }

    public void setMaxInteractionNumber(Integer interactionNumber) {
        this.maxInteractionNumber = interactionNumber;
    }
}
