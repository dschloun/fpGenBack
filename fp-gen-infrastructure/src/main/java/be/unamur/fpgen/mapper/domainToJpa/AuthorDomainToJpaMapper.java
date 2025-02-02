package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.entity.author.AuthorEntity;

import java.util.Objects;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class AuthorDomainToJpaMapper {

    public static AuthorEntity mapForCreate(final Author domain){
        if (Objects.isNull(domain)){
            return null;
        }

        final AuthorEntity entity = new AuthorEntity();
        entity.setLastname(domain.getLastName());
        entity.setFirstname(domain.getFirstName());
        entity.setTrigram(domain.getTrigram());
        entity.setOrganization(domain.getOrganization());
        entity.setFunction(domain.getFunction());
        entity.setEmail(domain.getEmail());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setAcceptTermsOfUse(domain.isAcceptTermsOfUse());
        entity.setMotivation(domain.getMotivation());
        entity.setStatus(domain.getStatus());
        entity.setAccountCreated(domain.isAccountCreated());

        return entity;
    }

    public static AuthorEntity map(final Author domain){

        final AuthorEntity entity = new AuthorEntity();
        entity.setId(domain.getId());
        entity.setCreationDate(domain.getCreationDate());
        entity.setModificationDate(domain.getModificationDate());
        entity.setLastname(domain.getLastName());
        entity.setFirstname(domain.getFirstName());
        entity.setTrigram(domain.getTrigram());
        entity.setOrganization(domain.getOrganization());
        entity.setFunction(domain.getFunction());
        entity.setEmail(domain.getEmail());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setAcceptTermsOfUse(domain.isAcceptTermsOfUse());
        entity.setMotivation(domain.getMotivation());
        entity.setStatus(domain.getStatus());
        entity.setAccountCreated(domain.isAccountCreated());

        return entity;
    }

    public static AuthorEntity mapForUpdate(final AuthorEntity entity, final Author domain){
        if (Objects.isNull(domain)){
            return null;
        }

        entity.setLastname(domain.getLastName());
        entity.setFirstname(domain.getFirstName());
        entity.setTrigram(domain.getTrigram());
        entity.setOrganization(domain.getOrganization());
        entity.setFunction(domain.getFunction());
        entity.setEmail(domain.getEmail());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setAcceptTermsOfUse(domain.isAcceptTermsOfUse());
        entity.setMotivation(domain.getMotivation());
        entity.setStatus(domain.getStatus());

        return entity;
    }
}
