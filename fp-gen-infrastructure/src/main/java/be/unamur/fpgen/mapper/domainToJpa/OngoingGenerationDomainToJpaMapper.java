package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;

public class OngoingGenerationDomainToJpaMapper {

    public static OngoingGenerationEntity mapForCreate(OngoingGeneration domain, AuthorEntity author) {
        final OngoingGenerationEntity entity = new OngoingGenerationEntity();
        entity.setType(domain.getType());
        entity.setStatus(domain.getStatus());
        entity.setAuthor(author);
        entity.setPromptVersion(domain.getPromptVersion());
        return entity;
    }
}
