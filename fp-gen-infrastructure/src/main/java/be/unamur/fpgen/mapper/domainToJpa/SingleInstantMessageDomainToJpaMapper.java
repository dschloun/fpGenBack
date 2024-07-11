package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.instant_message.SingleInstantMessageEntity;
import be.unamur.fpgen.instant_message.SingleInstantMessage;

public class SingleInstantMessageDomainToJpaMapper {

    private SingleInstantMessageDomainToJpaMapper(){
        // left empty intentionally
    }

    public static SingleInstantMessageEntity mapForCreate(final SingleInstantMessage domain){
        final SingleInstantMessageEntity entity = new SingleInstantMessageEntity();
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        return entity;
    }

    public static SingleInstantMessageEntity mapForUpdate(final SingleInstantMessage domain, final SingleInstantMessageEntity entity){
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        return entity;
    }
}
