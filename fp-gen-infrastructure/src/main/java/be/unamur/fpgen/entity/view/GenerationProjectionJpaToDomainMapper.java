package be.unamur.fpgen.entity.view;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.util.Objects;
import java.util.UUID;

public class GenerationProjectionJpaToDomainMapper {
    public static AbstractGeneration map(final GenerationProjection entity){
        if (Objects.isNull(entity)){
            return null;
        }
         if("IMG".equals(entity.getKind())){
             return InstantMessageGeneration.newBuilder()
                     .withId(UUID.fromString(entity.getId()))
                     //.withCreationDate(entity.getCreationDate())
                     .withType(MessageTypeEnum.valueOf(entity.getType()))
                     .withTopic(MessageTopicEnum.valueOf(entity.getTopic()))
                     .withUserPrompt(entity.getUserPrompt())
                     .withAuthor(Author.newBuilder().withTrigram(entity.getAuthorTrigram()).build())
                     .withDetails(entity.getDetails())
                     .withQuantity(entity.getQuantity())
                     .build();
         } else if("CMG".equals(entity.getKind())){
             return ConversationGeneration.newBuilder()
                     .withId(UUID.fromString(entity.getId()))
                     //.withCreationDate(entity.getCreationDate())
                     .withType(MessageTypeEnum.valueOf(entity.getType()))
                     .withTopic(MessageTopicEnum.valueOf(entity.getTopic()))
                     .withUserPrompt(entity.getUserPrompt())
                     .withAuthor(Author.newBuilder().withTrigram(entity.getAuthorTrigram()).build())
                     .withDetails(entity.getDetails())
                     .withQuantity(entity.getQuantity())
                     .build();
         } else {
             throw new IllegalArgumentException(String.format("Unknown kind %s", entity.getKind()));
         }
    }
}
