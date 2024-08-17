package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.utils.MapperUtil;

public class ConversationGenerationJpaToDomainMapper {

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
