package be.unamur.fpgen.entity.notification;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.notification.NotificationStatus;

import javax.persistence.*;

/**
 * Represents a notification sent by an author to another author.
 */
@Entity
@Table(name = "notification")
public class NotificationEntity extends BaseUuidEntity {
    private AuthorEntity sender;
    private AuthorEntity receiver;
    private NotificationStatus status;
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = true)
    public AuthorEntity getSender() {
        return sender;
    }

    public void setSender(AuthorEntity author) {
        this.sender = author;
    }

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    public AuthorEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(AuthorEntity receiver) {
        this.receiver = receiver;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    @Column(name = "message", nullable = false)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
