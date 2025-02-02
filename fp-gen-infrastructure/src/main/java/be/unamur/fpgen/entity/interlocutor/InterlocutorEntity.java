package be.unamur.fpgen.entity.interlocutor;


import be.unamur.fpgen.entity.base.BaseOnlyIntegerIdEntity;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;

import javax.persistence.*;

/**
 * Represents an interlocutor of a conversation
 */
@Entity
@Table(name = "interlocutor")
public class InterlocutorEntity extends BaseOnlyIntegerIdEntity {

    // members
    private InterlocutorTypeEnum interlocutorTypeEnum;
    private Integer number;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    public InterlocutorTypeEnum getInterlocutorTypeEnum() {
        return interlocutorTypeEnum;
    }

    public void setInterlocutorTypeEnum(InterlocutorTypeEnum interlocutorTypeEnum) {
        this.interlocutorTypeEnum = interlocutorTypeEnum;
    }

    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
