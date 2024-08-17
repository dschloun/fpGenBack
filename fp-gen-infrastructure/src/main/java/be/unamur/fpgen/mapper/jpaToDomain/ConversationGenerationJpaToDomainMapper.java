package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.utils.MapperUtil;

public class ConversationGenerationJpaToDomainMapper {

    public static Generation map(final ConversationGenerationEntity entity){
        if (entity == null){
            return null;
        }
        return Generation.newBuilder()
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
