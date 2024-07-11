package be.unamur.fpgen.instant_message;

import be.unamur.fpgen.BaseUuidDomain;

import java.time.OffsetDateTime;
import java.util.UUID;

public abstract class AbstractInstantMessage extends BaseUuidDomain {
    // members
    private InstantMessageTopicEnum topic;
    private InstantMessageTypeEnum type;
    private String content;

    // constructors
    protected AbstractInstantMessage(final UUID id,
                                     final OffsetDateTime creationDate,
                                     final OffsetDateTime modificationDate,
                                     final InstantMessageTopicEnum topic,
                                     final InstantMessageTypeEnum type,
                                     final String content) {
        super(id, creationDate, modificationDate);
        this.topic = topic;
        this.type = type;
        this.content = content;
    }

    // getters
    public InstantMessageTopicEnum getTopic() {
        return topic;
    }

    public InstantMessageTypeEnum getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    // builder
    public abstract static class AbstractInstantMessageBuilder<T> extends AbstractBaseUuidDomainBuilder<T> {
        private InstantMessageTopicEnum topic;
        private InstantMessageTypeEnum type;
        private String content;

        public InstantMessageTopicEnum getTopic() {
            return topic;
        }

        public InstantMessageTypeEnum getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        public T withTopic(final InstantMessageTopicEnum topic) {
            this.topic = topic;
            return self();
        }

        public T withType(final InstantMessageTypeEnum type) {
            this.type = type;
            return self();
        }

        public T withContent(final String content) {
            this.content = content;
            return self();
        }
    }
}
