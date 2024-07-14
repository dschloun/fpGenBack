package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.entity.author.AuthorEntity;

import java.util.Objects;

public class AuthorDomainToJpaMapper {

    public static AuthorEntity mapForCreate(final Author domain){
        if (Objects.isNull(domain)){
            return null;
        }

        final AuthorEntity entity = new AuthorEntity();
        entity.setLastName(domain.getLastName());
        entity.setFirstName(domain.getFirstName());
        entity.setTrigram(domain.getTrigram());
        entity.setOrganization(domain.getOrganization());
        entity.setFunction(domain.getFunction());
        entity.setEmail(domain.getEmail());
        entity.setPhoneNumber(domain.getPhoneNumber());

        return entity;
    }

    public static AuthorEntity mapForUpdate(final AuthorEntity entity, final Author domain){
        if (Objects.isNull(domain)){
            return null;
        }

        entity.setLastName(domain.getLastName());
        entity.setFirstName(domain.getFirstName());
        entity.setTrigram(domain.getTrigram());
        entity.setOrganization(domain.getOrganization());
        entity.setFunction(domain.getFunction());
        entity.setEmail(domain.getEmail());
        entity.setPhoneNumber(domain.getPhoneNumber());

        return entity;
    }
}
