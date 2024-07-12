package be.unamur.fpgen.entity;

import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
public class BaseOnlyLongIdEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1079657084325275787L;

    // members
    private Long id;

    // getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
