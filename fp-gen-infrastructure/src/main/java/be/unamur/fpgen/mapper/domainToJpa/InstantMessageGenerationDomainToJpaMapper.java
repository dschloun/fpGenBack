package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.AbstractGeneration;

public class InstantMessageGenerationDomainToJpaMapper {

    public static InstantMessageGenerationEntity mapForCreate(final AbstractGeneration domain, final AuthorEntity author) {
        final InstantMessageGenerationEntity entity = new InstantMessageGenerationEntity();
        entity.setGenerationId(domain.getGenerationId());
        entity.setAuthor(author);
        entity.setDetails(domain.getDetails());
        entity.setBatch(domain.isBatch());
        return entity;
    }
}
