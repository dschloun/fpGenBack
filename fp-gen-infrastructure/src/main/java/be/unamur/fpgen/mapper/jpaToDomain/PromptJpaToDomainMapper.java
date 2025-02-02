package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.PromptEntity;
import be.unamur.fpgen.prompt.Prompt;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class PromptJpaToDomainMapper {

    public static Prompt map(PromptEntity entity) {
        if (entity == null) {
            return null;
        }

        return Prompt.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withDatasetType(entity.getDatasetType())
                .withMessageType(entity.getMessageType())
                .withVersion(entity.getVersion())
                .withUserPrompt(entity.getUserPrompt())
                .withSystemPrompt(entity.getSystemPrompt())
                .withAuthor(AuthorJpaToDomainMapper.map(entity.getAuthor()))
                .withStatus(entity.getStatus())
                .withDefaultPrompt(entity.isDefaultPrompt())
                .withMotivation(entity.getMotivation())
                .build();
    }
}
