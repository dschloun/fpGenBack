package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.utils.MapperUtil;

import java.util.Objects;
import java.util.Optional;

public class DatasetJpaToDomainMapper {

    public static Dataset map(final DatasetEntity entity){
        if (entity instanceof InstantMessageDatasetEntity imd){
            return map(imd);
        } else if (entity instanceof ConversationDatasetEntity cd){
            return map(cd);
        } else {
            throw new IllegalArgumentException("Unknown dataset type");
        }
    }

    private static Dataset map(final InstantMessageDatasetEntity entity){
        if(Objects.isNull(entity)){
            return null;
        }
        return Dataset.newBuilder()
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
                .withItemList(MapperUtil.mapSet(entity.getInstantMessageGenerationList(), GenerationJpaToDomainMapper::map))
                .withOngoingGenerationId(Optional.ofNullable(entity.getOngoingGeneration()).map(BaseUuidEntity::getId).orElse(null))
                .withStatistic(StatisticJpaToDomainMapper.map(entity.getStatistic()))
                .withValidated(entity.isValidated())
                .withLastVersion(entity.isLastVersion())
                .withOriginalDatasetId(entity.getOriginalDatasetId())
                .build();
    }

    private static Dataset map(final ConversationDatasetEntity entity){
        if(entity == null){
            return null;
        }
        return Dataset.newBuilder()
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
                .withItemList(MapperUtil.mapSet(entity.getConversationGenerationList(), ConversationGenerationJpaToDomainMapper::map))
                .withOngoingGenerationId(Optional.ofNullable(entity.getOngoingGeneration()).map(BaseUuidEntity::getId).orElse(null))
                .withValidated(entity.isValidated())
                .withLastVersion(entity.isLastVersion())
                .withOriginalDatasetId(entity.getOriginalDatasetId())
                .build();
    }
}
