package be.unamur.fpgen.entity.base;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * @overview BaseEntity is a class that represents the base entity of the all entities.
 * BaseEntity is mutable
 * @specfield creation_date: OffsetDateTime // The creation date of the entity
 * @specfield modification_date: OffsetDateTime // The modification date of the entity
 * @invariant creation_date != null && modification_date != null
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1079657084325275787L;

    // members
    private OffsetDateTime creationDate;
    private OffsetDateTime modificationDate;

    /**
     * FA(c): c.creationDate = creation_date, c.modificationDate = modification_date
     * IR(c) : c.creationDate != null && c.modificationDate != null
     */

    /**
     * @return creation_date
     */
    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreatedDate
    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @requires creationDate != null
     * @modifies this.creation_date
     * @effects this.creation_date = creationDate
     */
    public void setCreationDate(final OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return modification_date
     */
    @Column(name = "modification_date", nullable = false)
    @LastModifiedDate
    public OffsetDateTime getModificationDate() {
        return modificationDate;
    }

    /**
     * @requires modificationDate != null
     * @modifies this.modification_date
     * @effects this.modification_date = modificationDate
     */
    public void setModificationDate(final OffsetDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }
}
