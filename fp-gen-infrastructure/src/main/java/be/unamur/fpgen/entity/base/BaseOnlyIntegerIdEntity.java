package be.unamur.fpgen.entity.base;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
public class BaseOnlyIntegerIdEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1079657084325275787L;

    // members
    private Integer id;

    // getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
