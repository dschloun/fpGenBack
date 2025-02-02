package be.unamur.fpgen.entity.conversation;

import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a conversation between a two interlocutors
 * The conversation has a type (e.g. SOCIAL_ENGINEERING) and a topic (e.g. WORK)
 * A conversation has several messages
 * e.g. message from interlocutor 1 to interlocutor 2
 * e.g. message from interlocutor 2 to interlocutor 1
 * ...
 * the hash is used to ensure an unicity of conversation
 */
@Entity
@Table(name = "conversation")
public class ConversationEntity extends BaseUuidEntity {

    // members
    private ConversationGenerationEntity conversationGeneration;
    private MessageTypeEnum type;
    private MessageTopicEnum topic;
    private Integer maxInteractionNumber;
    private Integer minInteractionNumber;
    private Set<ConversationInstantMessageEntity> messageSet = new HashSet<>();
    private String hash;

    // getters and setters
    @ManyToOne
    @JoinColumn(name = "generation_id")
    public ConversationGenerationEntity getConversationGeneration() {
        return conversationGeneration;
    }

    public void setConversationGeneration(ConversationGenerationEntity conversationGeneration) {
        this.conversationGeneration = conversationGeneration;
    }

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTypeEnum getType() {
        return type;
    }

    public void setType(MessageTypeEnum messageType) {
        this.type = messageType;
    }

    @Column(name = "topic", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTopicEnum getTopic() {
        return topic;
    }

    public void setTopic(MessageTopicEnum messageTopic) {
        this.topic = messageTopic;
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

    @Column(name = "min_interaction_number")
    public Integer getMinInteractionNumber() {
        return minInteractionNumber;
    }

    public void setMinInteractionNumber(Integer minInteractionNumber) {
        this.minInteractionNumber = minInteractionNumber;
    }

    @Column(name = "hash", nullable = false)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
