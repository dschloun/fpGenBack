package be.unamur.fpgen;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

public abstract class BaseDomain implements Serializable {
    @Serial
    private static final long serialVersionUID = 8324154726951439647L;

    // members
    protected OffsetDateTime creationDate;
    protected OffsetDateTime modificationDate;

    // constructors
    protected BaseDomain() {
        // Required no-arg constructor
    }

    public BaseDomain(final OffsetDateTime creationDate, final OffsetDateTime modificationDate) {
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    // getters
    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public OffsetDateTime getModificationDate() {
        return modificationDate;
    }

    // builder
    protected abstract static class AbstractBaseDomainBuilder<T> {
        private OffsetDateTime creationDate;
        private OffsetDateTime modificationDate;

        public OffsetDateTime getCreationDate() {
            return creationDate;
        }

        public OffsetDateTime getModificationDate() {
            return modificationDate;
        }

        abstract T self();

        public T withCreationDate(final OffsetDateTime creationDate) {
            this.creationDate = creationDate;
            return self();
        }

        public T withModificationDate(final OffsetDateTime modificationDate) {
            this.modificationDate = modificationDate;
            return self();
        }
    }
}
