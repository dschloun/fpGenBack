package be.unamur.fpgen.entity.instant_message;

import be.unamur.fpgen.entity.BaseUuidEntity;
import be.unamur.fpgen.instant_message.InstantMessageTopicEnum;
import be.unamur.fpgen.instant_message.InstantMessageTypeEnum;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serial;

@MappedSuperclass
public class InstantMessageEntity extends BaseUuidEntity {

    @Serial
    private static final long serialVersionUID = -80039190673154484L;

    // members
    private InstantMessageTopicEnum topic;
    private InstantMessageTypeEnum type;
    private String content;

    // getters and setters
    @Column(name = "topic", nullable = false)
    public InstantMessageTopicEnum getTopic() {
        return topic;
    }

    public void setTopic(final InstantMessageTopicEnum topic) {
        this.topic = topic;
    }

    @Column(name = "instant_message_type", nullable = false)
    public InstantMessageTypeEnum getType() {
        return type;
    }

    public void setType(final InstantMessageTypeEnum type) {
        this.type = type;
    }

    @Column(name = "content", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
