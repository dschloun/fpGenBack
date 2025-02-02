package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.PromptEntity;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.prompt.Prompt;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class PromptDomainToJpaMapper {

    public static PromptEntity mapForCreate(Prompt domain, AuthorEntity author) {
        if (domain == null) {
            return null;
        }

        PromptEntity entity = new PromptEntity();
        entity.setId(domain.getId());
        entity.setCreationDate(domain.getCreationDate());
        entity.setModificationDate(domain.getModificationDate());
        entity.setDatasetType(domain.getDatasetType());
        entity.setMessageType(domain.getMessageType());
        entity.setVersion(domain.getVersion());
        entity.setUserPrompt(domain.getUserPrompt());
        entity.setSystemPrompt(domain.getSystemPrompt());
        entity.setAuthor(author);
        entity.setStatus(domain.getStatus());
        entity.setDefaultPrompt(domain.isDefaultPrompt());
        entity.setMotivation(domain.getMotivation());
        return entity;
    }
}
