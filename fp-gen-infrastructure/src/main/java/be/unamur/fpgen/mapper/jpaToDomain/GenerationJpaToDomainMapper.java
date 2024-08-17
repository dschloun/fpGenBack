package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.entity.generation.GenerationEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.utils.MapperUtil;

public class GenerationJpaToDomainMapper {

    public static AbstractGeneration map(final GenerationEntity entity){
        if (entity == null){
            return null;
        }
        if(entity instanceof InstantMessageGenerationEntity){
            return map((InstantMessageGenerationEntity) entity);
        } else if (entity instanceof ConversationGenerationEntity) {
            return map((ConversationGenerationEntity) entity);
        } else {
            throw new IllegalArgumentException("Unknown generation type");
        }
    }

    public static AbstractGeneration map(final InstantMessageGenerationEntity entity){
        if (entity == null){
            return null;
        }
        return AbstractGeneration.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withAuthor(AuthorJpaToDomainMapper.map(entity.getAuthor()))
                .withDetails(entity.getDetails())
                .withQuantity(entity.getQuantity())
                .withQuantity(entity.getQuantity())
                .withTopic(entity.getTopic())
                .withType(entity.getType())
                .withSystemPrompt(entity.getSystemPrompt())
                .withUserPrompt(entity.getUserPrompt())
                .withItemList(MapperUtil.mapSet(entity.getInstantMessageList(), InstantMessageJpaToDomainMapper::map))
                .build();
    }

    public static AbstractGeneration map(final ConversationGenerationEntity entity){
        if (entity == null){
            return null;
        }
        return AbstractGeneration.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withAuthor(AuthorJpaToDomainMapper.map(entity.getAuthor()))
                .withDetails(entity.getDetails())
                .withQuantity(entity.getQuantity())
                .withTopic(entity.getTopic())
                .withType(entity.getType())
                .withSystemPrompt(entity.getSystemPrompt())
                .withUserPrompt(entity.getUserPrompt())
                .withItemList(MapperUtil.mapSet(entity.getConversationList(), ConversationJpaToDomainMapper::map))
                .build();
    }
}
