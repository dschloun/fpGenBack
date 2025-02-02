package be.unamur.fpgen.entity.instant_message;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.interlocutor.InterlocutorEntity;

import javax.persistence.*;

/**
 * Inherit from AbstractInstantMessageEntity
 * Conversation messages has two interlocutors a sender and a receiver
 * Order number is crucial to keep the order of the messages in the conversation
 */
@Entity
@DiscriminatorValue(value = "CIM")
public class ConversationInstantMessageEntity extends AbstractInstantMessageEntity {

    // members
    private ConversationEntity conversation;
    private InterlocutorEntity sender;
    private InterlocutorEntity receiver;
    private Integer orderNumber;

    // getters and setters
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    public ConversationEntity getConversation() {
        return conversation;
    }

    public void setConversation(final ConversationEntity conversation) {
        this.conversation = conversation;
    }

    @ManyToOne
    @JoinColumn(name = "sender_id")
    public InterlocutorEntity getSender() {
        return sender;
    }

    public void setSender(final InterlocutorEntity sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    public InterlocutorEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(final InterlocutorEntity receiver) {
        this.receiver = receiver;
    }

    @Column(name = "order_number")
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(final Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
