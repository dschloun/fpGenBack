package be.unamur.fpgen.message;

import be.unamur.fpgen.AbstractItem;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Abstract class for messages.
 * Here are the commune attributes of all messages.
 */
public abstract class AbstractInstantMessage extends AbstractItem {
    // members
    private MessageTopicEnum topic;
    private MessageTypeEnum type;
    private String content;
    private String generationId;
    private boolean batch;

    // constructors
    protected AbstractInstantMessage(final UUID id,
                                     final OffsetDateTime creationDate,
                                     final OffsetDateTime modificationDate,
                                     final MessageTopicEnum topic,
                                     final MessageTypeEnum type,
                                     final String content,
                                     final String generationId,
                                     final boolean batch) {
        super(id, creationDate, modificationDate);
        this.topic = topic;
        this.type = type;
        this.content = content;
        this.generationId = generationId;
        this.batch = batch;
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

    public String getGeneration() {
        return generationId;
    }

    public boolean isBatch() {
        return batch;
    }

    // builder
    public abstract static class AbstractInstantMessageBuilder<T> extends AbstractBaseUuidDomainBuilder<T> {
        private MessageTopicEnum topic;
        private MessageTypeEnum type;
        private String content;
        private String generationId;
        private boolean batch;

        public MessageTopicEnum getTopic() {
            return topic;
        }

        public MessageTypeEnum getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        public String getGeneration() {
            return generationId;
        }

        public boolean isBatch() {
            return batch;
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

        public T withGenerationId(final String generationId){
            this.generationId = generationId;
            return self();
        }

        public T withBatch(final boolean batch){
            this.batch = batch;
            return self();
        }
    }
}
