package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.entity.generation.GenerationEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.utils.MapperUtil;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class GenerationJpaToDomainMapper {

    public static Generation map(final GenerationEntity entity){
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

    public static Generation map(final InstantMessageGenerationEntity entity){
        if (entity == null){
            return null;
        }
        return Generation.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withGenerationType(GenerationTypeEnum.INSTANT_MESSAGE)
                .withAuthor(AuthorJpaToDomainMapper.map(entity.getAuthor()))
                .withDetails(entity.getDetails())
                .withQuantity(entity.getQuantity())
                .withQuantity(entity.getQuantity())
                .withTopic(entity.getTopic())
                .withType(entity.getType())
                .withPrompt(PromptJpaToDomainMapper.map(entity.getPrompt()))
                .withItemList(MapperUtil.mapSet(entity.getInstantMessageList(), InstantMessageJpaToDomainMapper::map))
                .build();
    }

    public static Generation map(final ConversationGenerationEntity entity){
        if (entity == null){
            return null;
        }
        return Generation.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withGenerationType(GenerationTypeEnum.CONVERSATION)
                .withAuthor(AuthorJpaToDomainMapper.map(entity.getAuthor()))
                .withDetails(entity.getDetails())
                .withQuantity(entity.getQuantity())
                .withTopic(entity.getTopic())
                .withType(entity.getType())
                .withPrompt(PromptJpaToDomainMapper.map(entity.getPrompt()))
                .withItemList(MapperUtil.mapSet(entity.getConversationList(), ConversationJpaToDomainMapper::map))
                .build();
    }
}
