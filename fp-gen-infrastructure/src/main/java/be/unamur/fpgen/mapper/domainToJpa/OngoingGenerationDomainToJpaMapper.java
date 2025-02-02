package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.utils.MapperUtil;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class OngoingGenerationDomainToJpaMapper {

    public static OngoingGenerationEntity mapForCreate(OngoingGeneration domain, AuthorEntity author) {
        final OngoingGenerationEntity entity = new OngoingGenerationEntity();
        entity.setType(domain.getType());
        entity.setStatus(domain.getStatus());
        entity.setAuthor(author);
        entity.setDatasetId(domain.getDatasetId());
        entity.setItemList(MapperUtil.mapSet(domain.getItemList(), i -> OngoingGenerationItemDomainToJpaMapper.mapForCreate(i, entity)));
        entity.setMinInteractionNumber(domain.getMinInteractionNumber());
        entity.setMaxInteractionNumber(domain.getMaxInteractionNumber());
        return entity;
    }
}
