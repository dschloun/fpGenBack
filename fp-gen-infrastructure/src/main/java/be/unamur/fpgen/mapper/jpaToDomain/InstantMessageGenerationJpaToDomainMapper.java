package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.utils.MapperUtil;

public class InstantMessageGenerationJpaToDomainMapper {

    public static InstantMessageGeneration mapInstantMessageGeneration(final InstantMessageGenerationEntity entity){
        return InstantMessageGeneration.newBuilder()
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
                .withInstantMessageList(MapperUtil.mapSet(entity.getInstantMessageList(), InstantMessageJpaToDomainMapper::map))
                .build();
    }

    public static InstantMessageGeneration map(final InstantMessageGenerationEntity entity){
        if (entity == null){
            return null;
        }

        return InstantMessageGeneration.newBuilder()
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
                .build();
    }
}
