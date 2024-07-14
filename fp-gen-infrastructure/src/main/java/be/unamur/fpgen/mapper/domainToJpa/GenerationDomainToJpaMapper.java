package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.Generation;

public class GenerationDomainToJpaMapper {

    public static InstantMessageGenerationEntity mapForCreate(final Generation domain){
        final InstantMessageGenerationEntity entity = new InstantMessageGenerationEntity();
        entity.setGenerationId(domain.getGenerationId());
        //entity.setAuthorTrigram(domain.getAuthorTrigram()); //todo replace by author
        entity.setDetails(domain.getDetails());
        entity.setBatch(domain.isBatch());
        return entity;
    }
}
