package be.unamur.fpgen.entity.instant_message;

import be.unamur.fpgen.entity.BaseUuidEntity;
import be.unamur.fpgen.entity.generation.GenerationEntity;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;

import javax.persistence.*;
import java.io.Serial;

/**
 * @overview
 * InstantMessageEntity is a mapped superclass for instant messages.
 * It provides the basic structure for instant messages.
 * InstantMessageEntity is mutable.
 * @specfield generationId: String // the generation id of the instant message
 * format = start by SIM if singleInstantMessage CIM if conversationInstantMessage + 'trigram + date + time': ex SIM_DSC202407120814
 * @specfield topic: MessageTopicEnum // the topic of the message
 * @specfield type: MessageTypeEnum // the type of the message
 * @specfield content: String // the content of the message
 * @invariant generationId != null && this.topic != null && this.type != null && this.content != null
 */
@Entity(name = "instant_message")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind", discriminatorType = DiscriminatorType.STRING)
public class InstantMessageEntity extends BaseUuidEntity {

    @Serial
    private static final long serialVersionUID = -80039190673154484L;

    // members
//    private GenerationEntity generation;
    private MessageTopicEnum topic;
    private MessageTypeEnum type;
    private String content;

    /**
     * FA(c): c.generationId = generationId, c.topic = topic, c.type = type, c.content = content
     * IR(c): c.generatoinId != null && c.topic != null && c.type != null && c.content != null
     */

    // getters and setters
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "generation_id")
//    public GenerationEntity getGeneration() {
//        return generation;
//    }
//
//    public void setGeneration(final GenerationEntity generation) {
//        this.generation = generation;
//    }

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
