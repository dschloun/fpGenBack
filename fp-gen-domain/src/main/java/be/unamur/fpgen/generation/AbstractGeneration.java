package be.unamur.fpgen.generation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.GenerationId;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @overview: generation is a class that represents the generation of a message, message batch, conversation or conversation batch
 * Generation is immutable.
 * @specfield generationId: String // technical identifier of the generation
 * @specfield generationDate: OffsetDateTime // date of the generation
 * @specfield authorTrigram: String // author trigram of the generation
 * format 'trigram' ex: JDO (John Doe)
 * @specfield details: String // details of the generation
 */
public abstract class AbstractGeneration extends BaseUuidDomain {
    // members
    protected String generationId;
    private final Author author;
    private final String details;
    private final Integer quantity;
    private final MessageTypeEnum type;
    private final MessageTopicEnum topic;
    private final String systemPrompt;
    private final String userPrompt;

    // constructors
    protected AbstractGeneration(final UUID id,
                                 final OffsetDateTime creationDate,
                                 final OffsetDateTime modificationDate,
                                 final Author author,
                                 final String details,
                                 final Integer quantity,
                                 final MessageTypeEnum type,
                                 final MessageTopicEnum topic,
                                 final String systemPrompt,
                                 final String userPrompt) {
        super(id, creationDate, modificationDate);
        this.author = author;
        this.details = details;
        this.quantity = quantity;
        this.type = type;
        this.topic = topic;
        this.systemPrompt = systemPrompt;
        this.userPrompt = userPrompt;
    }

    // getters
    public String getGenerationId() {
        return generationId;
    }

    public OffsetDateTime getGenerationDate() {
        return creationDate;
    }

    public Author getAuthor() {
        return author;
    }

    public String getDetails() {
        return details;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public MessageTopicEnum getTopic() {
        return topic;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    // builder
    protected abstract static class AbstractGenerationBuilder<T> extends AbstractBaseUuidDomainBuilder<T> implements GenerationId {
        protected String generationId;
        private Author author;
        private String details;
        private Integer quantity;
        private MessageTypeEnum type;
        private MessageTopicEnum topic;
        private String systemPrompt;
        private String userPrompt;

        public String getGenerationId() {
            return generationId;
        }

        public Author getAuthor() {
            return author;
        }

        public String getDetails() {
            return details;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public MessageTypeEnum getType() {
            return type;
        }

        public MessageTopicEnum getTopic() {
            return topic;
        }

        public String getSystemPrompt() {
            return systemPrompt;
        }

        public String getUserPrompt() {
            return userPrompt;
        }

        public T withAuthor(final Author author) {
            this.author = author;
            return self();
        }

        public T withDetails(final String details) {
            this.details = details;
            return self();
        }

        public T withQuantity(final Integer quantity) {
            this.quantity = quantity;
            return self();
        }

        public T withType(final MessageTypeEnum type) {
            this.type = type;
            return self();
        }

        public T withTopic(final MessageTopicEnum topic) {
            this.topic = topic;
            return self();
        }

        public T withSystemPrompt(final String systemPrompt) {
            this.systemPrompt = systemPrompt;
            return self();
        }

        public T withUserPrompt(final String userPrompt) {
            this.userPrompt = userPrompt;
            return self();
        }

        protected String returnBatchOrSingle(){
            return this.quantity > 1 ? "BATCH" : "SINGLE";
        }
    }
}
