package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.instant_message.InstantMessage;

/**
 * @overview
 * InstantMessageDomainToJpaMapper is a utility class providing methods to map InstantMessage domain objects to
 * InstantMessageEntity JPA objects.
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
    public static InstantMessageEntity mapForCreate(final InstantMessage domain, final Generation generationDomain){
        final InstantMessageEntity entity = new InstantMessageEntity();
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        entity.setInstantMessageGeneration(GenerationDomainToJpaMapper.mapForCreate(generationDomain));
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
