package be.unamur.fpgen.dataset;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;

import java.time.OffsetDateTime;
import java.util.UUID;

public abstract class AbstractDataset extends BaseUuidDomain {
    private final String businessId;
    private final String version;
    private final String name;
    private final String description;
    private final String comment;
    private final Author author;

    protected AbstractDataset (final UUID id,
                               final OffsetDateTime creationDate,
                               final OffsetDateTime modificationDate,
                               final String businessId,
                               final String version,
                               final String name,
                               final String description,
                               final String comment,
                               final Author author) {
        super(id, creationDate, modificationDate);
        this.businessId = businessId;
        this.version = version;
        this.name = name;
        this.description = description;
        this.comment = comment;
        this.author = author;
    }
    public String getBusinessId() {
        return businessId;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }

    public Author getAuthor() {
        return author;
    }

    protected abstract static class AbstractDatasetBuilder<T> extends AbstractBaseUuidDomainBuilder<T> {
        private String businessId;
        private String version;
        private String name;
        private String description;
        private String comment;
        private Author author;

        public String getBusinessId() {
            return businessId;
        }

        public String getVersion() {
            return version;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getComment() {
            return comment;
        }

        public Author getAuthor() {
            return author;
        }

        public T withBusinessId(String businessId) {
            this.businessId = businessId;
            return self();
        }
        public T withVersion(String version) {
            this.version = version;
            return self();
        }
        public T withName(String name) {
            this.name = name;
            return self();
        }

        public T withDescription(String description) {
            this.description = description;
            return self();
        }
        public T withComment(String comment) {
            this.comment = comment;
            return self();
        }
        public T withAuthor(Author author) {
            this.author = author;
            return self();
        }
    }
}
