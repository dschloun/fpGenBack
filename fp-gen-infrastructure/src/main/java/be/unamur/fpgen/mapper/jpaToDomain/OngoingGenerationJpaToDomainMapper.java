package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.utils.MapperUtil;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class OngoingGenerationJpaToDomainMapper {

    public static OngoingGeneration map(be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity entity) {
        return OngoingGeneration.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withType(entity.getType())
                .withItemList(MapperUtil.mapSet(entity.getItemList(), OngoingGenerationItemJpaToDomainMapper::map))
                .withAuthor(AuthorJpaToDomainMapper.map(entity.getAuthor()))
                .withStatus(entity.getStatus())
                .withDatasetId(entity.getDatasetId())
                .withMinInteractionNumber(entity.getMinInteractionNumber())
                .withMaxInteractionNumber(entity.getMaxInteractionNumber())
                .build();
    }
}