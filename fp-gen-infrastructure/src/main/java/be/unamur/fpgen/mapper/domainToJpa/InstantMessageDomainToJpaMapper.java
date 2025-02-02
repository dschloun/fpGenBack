package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.message.InstantMessage;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class InstantMessageDomainToJpaMapper {

    private InstantMessageDomainToJpaMapper(){
        // left empty intentionally
    }

    /**
     * Maps the given InstantMessage domain object to a new InstantMessageEntity JPA object.
     * The goal is for creating a new InstantMessageEntity object to be persisted in the database.
     * @requires domain != null
     * @return a InstantMessageEntity object mapped from the given InstantMessage domain object
     */
    public static InstantMessageEntity mapForCreate(final InstantMessage domain, final InstantMessageGenerationEntity generation){
        final InstantMessageEntity entity = new InstantMessageEntity();
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        entity.setInstantMessageGeneration(generation);
        entity.setHash(domain.getHash());
        return entity;
    }

    public static InstantMessageEntity map(final InstantMessage domain){
        final InstantMessageEntity entity = new InstantMessageEntity();
        entity.setId(domain.getId());
        entity.setCreationDate(domain.getCreationDate());
        entity.setModificationDate(domain.getModificationDate());
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        entity.setHash(domain.getHash());
        return entity;
    }

    /**
     * Maps the given InstantMessage domain object to the given InstantMessageEntity JPA object.
     * The goal is for updating an existing InstantMessageEntity object in the database.
     * @requires domain != null && entity != null
     * @return the given InstantMessageEntity object mapped from the given InstantMessage domain object
     */
    public static InstantMessageEntity mapForUpdate(final InstantMessage domain, final InstantMessageEntity entity){
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        return entity;
    }
}
