package be.unamur.fpgen.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1079657084325275787L;

    private OffsetDateTime creationDate;
    private OffsetDateTime modificationDate;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreatedDate
    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "modification_date", nullable = false)
    @LastModifiedDate
    public OffsetDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(OffsetDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }
}
