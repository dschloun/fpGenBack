package be.unamur.fpgen.entity.view;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.Prompt;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class GenerationProjectionJpaToDomainMapper {
    public static Generation map(final GenerationProjection entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return Generation.newBuilder()
                .withId(UUID.fromString(entity.getId()))
                .withCreationDate(OffsetDateTime.ofInstant(entity.getCreationDate().toInstant(), ZoneOffset.UTC))
                .withGenerationType(convertKindToGenerationType(entity.getKind()))
                .withType(MessageTypeEnum.valueOf(entity.getType()))
                .withTopic(MessageTopicEnum.valueOf(entity.getTopic()))
                .withPrompt(Prompt.newBuilder().withVersion(entity.getPromptVersion()).build())
                .withAuthor(Author.newBuilder().withTrigram(entity.getAuthorTrigram()).build())
                .withDetails(entity.getDetails())
                .withQuantity(entity.getQuantity())
                .build();

    }

    private static GenerationTypeEnum convertKindToGenerationType(String kind) {
        return switch (kind) {
            case "IMG" -> GenerationTypeEnum.INSTANT_MESSAGE;
            case "CMG" -> GenerationTypeEnum.CONVERSATION;
            default -> null;
        };
    }
}
