package be.unamur.fpgen.entity.instant_message;

import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import javax.persistence.*;
import java.io.Serial;

/**
 * Abstract class for messages.
 * Here are the commune attributes of all messages.
 */
@Entity
@Table(name = "instant_message")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind", discriminatorType = DiscriminatorType.STRING)
public class AbstractInstantMessageEntity extends BaseUuidEntity {

    @Serial
    private static final long serialVersionUID = -80039190673154484L;

    // members
    private MessageTopicEnum topic;
    private MessageTypeEnum type;
    private String content;

    /**
     * @return topic
     */
    @Column(name = "topic", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTopicEnum getTopic() {
        return topic;
    }

    /**
     * @requires topic != null
     * @modifies this.topic
     * @effects this.topic = topic
     */
    public void setTopic(final MessageTopicEnum topic) {
        this.topic = topic;
    }

    /**
     * @return type
     */
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTypeEnum getType() {
        return type;
    }

    /**
     * @requires type != null
     * @modifies this.type
     * @effects this.type = type
     */
    public void setType(final MessageTypeEnum type) {
        this.type = type;
    }

    /**
     * @return content
     */
    @Column(name = "content", nullable = false)
    public String getContent() {
        return content;
    }

    /**
     * @requires content != null
     * @modifies this.content
     * @effects this.content = content
     */
    public void setContent(final String content) {
        this.content = content;
    }
}
