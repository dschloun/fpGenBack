package be.unamur.fpgen.instant_message;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.generation.Generation;

import java.time.OffsetDateTime;
import java.util.UUID;

public abstract class AbstractInstantMessage extends BaseUuidDomain {
    // members
    private MessageTopicEnum topic;
    private MessageTypeEnum type;
    private String content;
    private Generation generation;

    // constructors
    protected AbstractInstantMessage(final UUID id,
                                     final OffsetDateTime creationDate,
                                     final OffsetDateTime modificationDate,
                                     final MessageTopicEnum topic,
                                     final MessageTypeEnum type,
                                     final String content,
                                     final Generation generation) {
        super(id, creationDate, modificationDate);
        this.topic = topic;
        this.type = type;
        this.content = content;
        this.generation = generation;
    }

    // getters
    public MessageTopicEnum getTopic() {
        return topic;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public Generation getGeneration() {
        return generation;
    }

    // builder
    public abstract static class AbstractInstantMessageBuilder<T> extends AbstractBaseUuidDomainBuilder<T> {
        private MessageTopicEnum topic;
        private MessageTypeEnum type;
        private String content;
        private Generation generation;

        public MessageTopicEnum getTopic() {
            return topic;
        }

        public MessageTypeEnum getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        public Generation getGeneration() {
            return generation;
        }

        public T withTopic(final MessageTopicEnum topic) {
            this.topic = topic;
            return self();
        }

        public T withType(final MessageTypeEnum type) {
            this.type = type;
            return self();
        }

        public T withContent(final String content) {
            this.content = content;
            return self();
        }

        public T withGeneration(final Generation generation){
            this.generation = generation;
            return self();
        }
    }
}
