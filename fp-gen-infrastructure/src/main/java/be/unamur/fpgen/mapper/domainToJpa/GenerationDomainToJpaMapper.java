package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.PromptEntity;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.entity.generation.GenerationEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class GenerationDomainToJpaMapper {

    public static GenerationEntity mapForCreate(final Generation domain, final AuthorEntity author, final PromptEntity prompt) {
        if (GenerationTypeEnum.INSTANT_MESSAGE.equals(domain.getGenerationType())){
            return mapForCreateInstantMessageGeneration(domain, author, prompt);
        } else {
            return mapForCreateConversationGeneration(domain, author, prompt);
        }
    }

    public static InstantMessageGenerationEntity mapForCreateInstantMessageGeneration(final Generation domain, final AuthorEntity author, final PromptEntity prompt) {
        final InstantMessageGenerationEntity entity = new InstantMessageGenerationEntity();
        entity.setGenerationId(domain.getGenerationId());
        entity.setAuthor(author);
        entity.setDetails(domain.getDetails());
        entity.setQuantity(domain.getQuantity());
        entity.setType(domain.getType());
        entity.setTopic(domain.getTopic());
        entity.setPrompt(prompt);
        return entity;
    }

    public static ConversationGenerationEntity mapForCreateConversationGeneration(final Generation domain, final AuthorEntity author, final PromptEntity prompt) {
        final ConversationGenerationEntity entity = new ConversationGenerationEntity();
        entity.setAuthor(author);
        entity.setGenerationId(domain.getGenerationId());
        entity.setDetails(domain.getDetails());
        entity.setQuantity(domain.getQuantity());
        entity.setType(domain.getType());
        entity.setTopic(domain.getTopic());
        entity.setPrompt(prompt);
        return entity;
    }
}
