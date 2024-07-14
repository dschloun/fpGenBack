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
                .withBatch(entity.isBatch())
                .withInstantMessageList(MapperUtil.mapSet(entity.getInstantMessageList(), InstantMessageJpaToDomainMapper::map))
                .build();
    }
}
