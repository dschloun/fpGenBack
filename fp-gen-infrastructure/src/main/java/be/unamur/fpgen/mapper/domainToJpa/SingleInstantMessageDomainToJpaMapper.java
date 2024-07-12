package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.instant_message.SingleInstantMessageEntity;
import be.unamur.fpgen.instant_message.SingleInstantMessage;

/**
 * @overview
 * SingleInstantMessageDomainToJpaMapper is a utility class providing methods to map SingleInstantMessage domain objects to
 * SingleInstantMessageEntity JPA objects.
 */
public class SingleInstantMessageDomainToJpaMapper {

    private SingleInstantMessageDomainToJpaMapper(){
        // left empty intentionally
    }

    /**
     * Maps the given SingleInstantMessage domain object to a new SingleInstantMessageEntity JPA object.
     * The goal is for creating a new SingleInstantMessageEntity object to be persisted in the database.
     * @requires domain != null
     * @return a SingleInstantMessageEntity object mapped from the given SingleInstantMessage domain object
     */
    public static SingleInstantMessageEntity mapForCreate(final SingleInstantMessage domain){
        final SingleInstantMessageEntity entity = new SingleInstantMessageEntity();
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        return entity;
    }

    /**
     * Maps the given SingleInstantMessage domain object to the given SingleInstantMessageEntity JPA object.
     * The goal is for updating an existing SingleInstantMessageEntity object in the database.
     * @requires domain != null && entity != null
     * @return the given SingleInstantMessageEntity object mapped from the given SingleInstantMessage domain object
     */
    public static SingleInstantMessageEntity mapForUpdate(final SingleInstantMessage domain, final SingleInstantMessageEntity entity){
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        return entity;
    }
}
