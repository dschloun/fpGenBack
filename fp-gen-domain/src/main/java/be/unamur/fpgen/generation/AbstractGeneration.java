package be.unamur.fpgen.generation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.GenerationId;

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
    private final boolean batch;

    // constructors
    protected AbstractGeneration(final UUID id,
                                 final OffsetDateTime creationDate,
                                 final OffsetDateTime modificationDate,
                                 final Author author,
                                 final String details,
                                 final boolean batch) {
        super(id, creationDate, modificationDate);
        this.author = author;
        this.details = details;
        this.batch = batch;
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

    public boolean isBatch() {
        return batch;
    }

    // builder
    protected abstract static class AbstractGenerationBuilder<T> extends AbstractBaseUuidDomainBuilder<T> implements GenerationId {
        private String generationId;
        private Author author;
        private String details;
        private boolean batch;

        public String getGenerationId() {
            return generationId;
        }

        public Author getAuthor() {
            return author;
        }

        public String getDetails() {
            return details;
        }

        public boolean isBatch() {
            return batch;
        }

        public T withAuthor(final Author author) {
            this.author = author;
            return self();
        }

        public T withDetails(final String details) {
            this.details = details;
            return self();
        }

        public T withBatch(final boolean batch) {
            this.batch = batch;
            return self();
        }

        protected String returnBatchOrSingle(){
            return this.batch ? "BATCH" : "SINGLE";
        }
    }
}
