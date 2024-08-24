package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.entity.generation.GenerationEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;

public class GenerationDomainToJpaMapper {

    public static GenerationEntity mapForCreate(final Generation domain, final AuthorEntity author) {
        if (GenerationTypeEnum.INSTANT_MESSAGE.equals(domain.getGenerationType())){
            return mapForCreateInstantMessageGeneration(domain, author);
        } else {
            return mapForCreateConversationGeneration(domain, author);
        }
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

    public static InstantMessageGenerationEntity mapForCreateInstantMessageGeneration(final Generation domain, final AuthorEntity author) {
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

    public static ConversationGenerationEntity mapForCreateConversationGeneration(final Generation domain, final AuthorEntity author) {
        final ConversationGenerationEntity entity = new ConversationGenerationEntity();
        entity.setAuthor(author);
        entity.setGenerationId(domain.getGenerationId());
        entity.setDetails(domain.getDetails());
        entity.setQuantity(domain.getQuantity());
        entity.setType(domain.getType());
        entity.setTopic(domain.getTopic());
        entity.setSystemPrompt(domain.getSystemPrompt());
        entity.setUserPrompt(domain.getUserPrompt());
        return entity;
    }
}
