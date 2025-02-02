package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationItemEntity;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class OngoingGenerationItemDomainToJpaMapper {

    public static OngoingGenerationItemEntity mapForCreate(OngoingGenerationItem domain, OngoingGenerationEntity ongoingGenerationEntity) {
        final OngoingGenerationItemEntity entity = new OngoingGenerationItemEntity();
        entity.setOngoingGeneration(ongoingGenerationEntity);
        entity.setMessageType(domain.getMessageType());
        entity.setMessageTopic(domain.getMessageTopic());
        entity.setQuantity(domain.getQuantity());
        entity.setStatus(domain.getStatus());
        entity.setPromptId(domain.getPromptId());
        return entity;
    }
}
