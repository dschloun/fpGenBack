package be.unamur.fpgen.entity.base;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serial;
import java.util.UUID;

/**
 * @overview BaseUuidEntity is the base class for all entities that have a UUID as primary key.
 * BaseUuid is mutable.
 * BaseUuidEntity is a subclass of BaseEntity.
 * @specfield id: UUID // the primary key
 * @invariant id != null
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseUuidEntity extends BaseEntity{

    @Serial
    private static final long serialVersionUID = 6727154608453465261L;

    // members
    private UUID id;

    /**
     * FA(c): c.id = id
     * IR(c): c.id != null
     */

    /**
     * @return id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public UUID getId() {
        return id;
    }

    /**
     * @requires id != null
     * @modifies this.id
     * @effects this.id = id
     */
    public void setId(final UUID id) {
        this.id = id;
    }
}
