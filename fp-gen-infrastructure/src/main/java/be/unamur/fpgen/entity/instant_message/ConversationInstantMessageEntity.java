package be.unamur.fpgen.entity.instant_message;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.interlocutor.InterlocutorEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "CIM")
public class ConversationInstantMessageEntity extends AbstractInstantMessageEntity {

    // members
    private ConversationEntity conversation;
    private InterlocutorEntity sender;
    private InterlocutorEntity receiver;

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
}
