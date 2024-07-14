package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.interlocutor.InterlocutorEntity;
import be.unamur.fpgen.interlocutor.Interlocutor;

import java.util.Objects;

public class InterlocutorJpaToDomainMapper {

    public static Interlocutor map(final InterlocutorEntity entity){
        if(Objects.isNull(entity)){
            return null;
        }
        return Interlocutor.newBuilder()
                .withId(entity.getId())
                .withType(entity.getInterlocutorTypeEnum())
                .build();
    }
}
