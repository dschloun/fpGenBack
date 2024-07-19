package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.utils.MapperUtil;

import java.util.Objects;

public class InstantMessageDatasetJpaToDomainMapper {

    public static InstantMessageDataset map(final InstantMessageDatasetEntity entity){
        if(Objects.isNull(entity)){
            return null;
        }
        return InstantMessageDataset.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withBusinessId(entity.getBusinessId())
                .withVersion(entity.getVersion())
                .withName(entity.getName())
                .withDescription(entity.getDescription())
                .withComment(entity.getComment())
                .withAuthor(AuthorJpaToDomainMapper.map(entity.getAuthor()))
                .withDatasetFunction(entity.getFunction())
                .withInstantMessageGenerationList(MapperUtil.mapSet(entity.getInstantMessageGenerationList(), InstantMessageGenerationJpaToDomainMapper::mapInstantMessageGeneration))
                .build();
    }
}
