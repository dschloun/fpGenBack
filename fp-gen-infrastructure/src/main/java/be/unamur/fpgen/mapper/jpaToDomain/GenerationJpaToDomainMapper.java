package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.Generation;

public class GenerationJpaToDomainMapper {

    public static Generation mapInstantMessageGeneration(final InstantMessageGenerationEntity entity){
        return Generation.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withAuthorTrigram(entity.getAuthor().getTrigram())
                .withDetails(entity.getDetails())
                .withBatch(entity.isBatch())
                .build();
    }
}
