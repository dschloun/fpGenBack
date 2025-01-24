package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.interlocutor.InterlocutorEntity;
import be.unamur.fpgen.interlocutor.Interlocutor;

public class InterlocutorDomainToJpaMapper {

    public static InterlocutorEntity mapForCreate(final Interlocutor domain){
        if(domain == null){
            return null;
        }
        final InterlocutorEntity entity = new InterlocutorEntity();
        entity.setInterlocutorTypeEnum(domain.getType());
        return entity;
    }

    public static InterlocutorEntity map(final Interlocutor domain){
        if(domain == null){
            return null;
        }
        final InterlocutorEntity entity = new InterlocutorEntity();
        entity.setId(domain.getId());
        entity.setInterlocutorTypeEnum(domain.getType());
        entity.setNumber(domain.getNumber());
        return entity;
    }
}
