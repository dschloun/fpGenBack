package be.unamur.fpgen.dataset;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;

import java.time.OffsetDateTime;
import java.util.UUID;

public abstract class AbstractDataset extends BaseUuidDomain {
    protected String businessId;
    private final Integer version;
    private final String name;
    private final String description;
    private final String comment;
    private final Author author;
    private final DatasetFunctionEnum datasetFunction;
    private final UUID ongoingGenerationId;

    protected AbstractDataset (final UUID id,
                               final OffsetDateTime creationDate,
                               final OffsetDateTime modificationDate,
                               final String businessId,
                               final Integer version,
                               final String name,
                               final String description,
                               final String comment,
                               final Author author,
                               final DatasetFunctionEnum datasetFunction,
                               final UUID ongoingGenerationId) {
        super(id, creationDate, modificationDate);
        this.businessId = businessId;
        this.version = version;
        this.name = name;
        this.description = description;
        this.comment = comment;
        this.author = author;
        this.datasetFunction = datasetFunction;
        this.ongoingGenerationId = ongoingGenerationId;
    }
    public String getBusinessId() {
        return businessId;
    }

    public Integer getVersion() {
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

    public DatasetFunctionEnum getDatasetFunction() {
        return datasetFunction;
    }

    public UUID getOngoingGenerationId() {
        return ongoingGenerationId;
    }

    protected abstract static class AbstractDatasetBuilder<T> extends AbstractBaseUuidDomainBuilder<T> implements GenerationId {
        protected String businessId;
        private Integer version;
        private String name;
        private String description;
        private String comment;
        private Author author;
        private DatasetFunctionEnum datasetFunction;
        private UUID ongoingGenerationId;

        public String getBusinessId() {
            return businessId;
        }

        public Integer getVersion() {
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

        public DatasetFunctionEnum getDatasetFunction() {
            return datasetFunction;
        }

        public UUID getOngoingGenerationId() {
            return ongoingGenerationId;
        }

        public T withBusinessId(String businessId) {
            this.businessId = businessId;
            return self();
        }
        public T withVersion(Integer version) {
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

        public T withDatasetFunction(DatasetFunctionEnum datasetFunction) {
            this.datasetFunction = datasetFunction;
            return self();
        }

        public T withOngoingGenerationId(UUID ongoingGenerationId) {
            this.ongoingGenerationId = ongoingGenerationId;
            return self();
        }
    }
}
