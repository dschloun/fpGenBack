package be.unamur.fpgen;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * @overview BaseDomain is a class that represents the base entity of the all entities.
 * BaseDomain is mutable
 * @specfield creation_date: OffsetDateTime // The creation date of the entity
 * @specfield modification_date: OffsetDateTime // The modification date of the entity
 * @invariant creation_date != null && modification_date != null
 */
public abstract class BaseDomain implements Serializable {
    @Serial
    private static final long serialVersionUID = 8324154726951439647L;

    // members
    protected OffsetDateTime creationDate;
    protected OffsetDateTime modificationDate;

    /**
     * FA(c): c.creationDate = creation_date, c.modificationDate = modification_date
     * IR(c) : c.creationDate != null && c.modificationDate != null
     */

    // constructors
    protected BaseDomain() {
        // Required no-arg constructor
    }

    /**
     * requires creationDate != null && modificationDate != null
     * @effects this.creationDate = creationDate, this.modificationDate = modificationDate
     */
    public BaseDomain(final OffsetDateTime creationDate, final OffsetDateTime modificationDate) {
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    // getters
    /**
     * @return creation_date
     */
    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @return modification_date
     */
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
