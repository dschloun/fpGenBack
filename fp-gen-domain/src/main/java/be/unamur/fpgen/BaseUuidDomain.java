package be.unamur.fpgen;

import java.io.Serial;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @overview BaseUuidDomain is the base class for all domain that have a UUID as primary key.
 * BaseUuid is immutable.
 * BaseUuidDomain is a subclass of BaseDomain.
 * @specfield id: UUID // the primary key
 * @invariant id != null
 */
public abstract class BaseUuidDomain extends BaseDomain {
    @Serial
    private static final long serialVersionUID = 7314488387561841349L;

    // members
    protected UUID id;

    /**
     * FA(c): c.id = id
     * IR(c): c.id != null
     */

    // constructors
    protected BaseUuidDomain() {
        // Required no-arg constructor
    }

    /**
     * @requires id != null, creationDate != null, modificationDate != null
     * @effects this.id = id, this.creationDate = creationDate, this.modificationDate = modificationDate
     */
    protected BaseUuidDomain(final UUID id, final OffsetDateTime creationDate, final OffsetDateTime modificationDate) {
        super(creationDate, modificationDate);
        this.id = id;
    }

    // getters

    /**
     * @return id
     */
    public UUID getId() {
        return id;
    }

    // builder
    protected abstract static class AbstractBaseUuidDomainBuilder<T> extends AbstractBaseDomainBuilder<T> {
        protected UUID id;

        public UUID getId() {
            return id;
        }

        protected abstract T self();

        public T withId(final UUID id) {
            this.id = id;
            return self();
        }
    }
}
