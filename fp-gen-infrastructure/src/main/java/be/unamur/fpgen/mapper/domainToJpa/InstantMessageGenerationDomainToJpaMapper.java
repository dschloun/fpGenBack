package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.AbstractGeneration;

public class InstantMessageGenerationDomainToJpaMapper {

    public static InstantMessageGenerationEntity mapForCreate(final AbstractGeneration domain, final AuthorEntity author) {
        final InstantMessageGenerationEntity entity = new InstantMessageGenerationEntity();
        entity.setGenerationId(domain.getGenerationId());
        entity.setAuthor(author);
        entity.setDetails(domain.getDetails());
        entity.setQuantity(domain.getQuantity());
        entity.setType(domain.getType());
        entity.setTopic(domain.getTopic());
        entity.setSystemPrompt(domain.getSystemPrompt());
        entity.setUserPrompt(domain.getUserPrompt());
        return entity;
    }

    public static InstantMessageGenerationEntity map(final AbstractGeneration domain) {
        final InstantMessageGenerationEntity entity = new InstantMessageGenerationEntity();
        entity.setId(domain.getId());
        entity.setModificationDate(domain.getModificationDate());
        entity.setCreationDate(domain.getCreationDate());
        entity.setGenerationId(domain.getGenerationId());
        entity.setAuthor(AuthorDomainToJpaMapper.map(domain.getAuthor()));
        entity.setDetails(domain.getDetails());
        entity.setQuantity(domain.getQuantity());
        entity.setType(domain.getType());
        entity.setTopic(domain.getTopic());
        entity.setSystemPrompt(domain.getSystemPrompt());
        entity.setUserPrompt(domain.getUserPrompt());
        return entity;
    }
}
