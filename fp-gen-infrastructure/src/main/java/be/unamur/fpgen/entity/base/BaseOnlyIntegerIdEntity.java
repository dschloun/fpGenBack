package be.unamur.fpgen.entity.base;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
public class BaseOnlyIntegerIdEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1079657084325275787L;

    // members
    private Integer id;

    // getters and setters
    @Id
    @SequenceGenerator(name = "interlocutor_id_seq", sequenceName = "interlocutor_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interlocutor_id_seq")
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
