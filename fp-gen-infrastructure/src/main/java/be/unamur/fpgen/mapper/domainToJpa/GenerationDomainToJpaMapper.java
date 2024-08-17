package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.generation.GenerationEntity;
import be.unamur.fpgen.generation.Generation;

public class GenerationDomainToJpaMapper {

    public static GenerationEntity mapForCreate(final Generation domain, final AuthorEntity author) {
        final GenerationEntity entity = new GenerationEntity();
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

    public static GenerationEntity map(final Generation domain) {
        final GenerationEntity entity = new GenerationEntity();
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
