package be.unamur.fpgen.entity.interlocutor;


import be.unamur.fpgen.entity.base.BaseOnlyIntegerIdEntity;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "interlocutor")
public class InterlocutorEntity extends BaseOnlyIntegerIdEntity {

    // members
    private InterlocutorTypeEnum interlocutorTypeEnum;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    public InterlocutorTypeEnum getInterlocutorTypeEnum() {
        return interlocutorTypeEnum;
    }

    public void setInterlocutorTypeEnum(InterlocutorTypeEnum interlocutorTypeEnum) {
        this.interlocutorTypeEnum = interlocutorTypeEnum;
    }

}
