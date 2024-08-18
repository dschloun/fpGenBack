package be.unamur.fpgen.entity.view;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.util.Objects;
import java.util.UUID;

public class GenerationProjectionJpaToDomainMapper {
    public static Generation map(final GenerationProjection entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return Generation.newBuilder()
                .withId(UUID.fromString(entity.getId()))
                .withCreationDate(entity.getCreationDate())
                .withGenerationType(convertKindToGenerationType(entity.getKind()))
                .withType(MessageTypeEnum.valueOf(entity.getType()))
                .withTopic(MessageTopicEnum.valueOf(entity.getTopic()))
                .withUserPrompt(entity.getUserPrompt())
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
