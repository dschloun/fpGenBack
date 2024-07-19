package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.generation.ConversationGeneration;

public class ConversationGenerationDomainToJpaMapper {

    public static ConversationGenerationEntity mapForCreate(final ConversationGeneration domain, final AuthorEntity author) {
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
