package be.unamur.fpgen;

import java.time.OffsetDateTime;
import java.util.UUID;

public abstract class AbstractItem extends BaseUuidDomain {

    public AbstractItem(UUID id, OffsetDateTime creationDate, OffsetDateTime modificationDate) {
        super(id, creationDate, modificationDate);
    }

    public abstract static class AbstractItemBuilder<T> extends AbstractBaseUuidDomainBuilder<T> {

        protected abstract T self();
    }
}
