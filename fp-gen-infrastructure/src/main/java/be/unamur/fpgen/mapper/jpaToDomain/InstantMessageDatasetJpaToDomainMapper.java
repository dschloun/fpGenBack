package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.utils.MapperUtil;

import java.util.Objects;
import java.util.Optional;

public class InstantMessageDatasetJpaToDomainMapper {

    public static InstantMessageDataset mapInstantMessageDataset(final InstantMessageDatasetEntity entity){
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
                .withOngoingGenerationId(Optional.ofNullable(entity.getOngoingGeneration()).map(BaseUuidEntity::getId).orElse(null))
                .withStatistic(StatisticJpaToDomainMapper.map(entity.getStatistic()))
                .withValidated(entity.isValidated())
                .withLastVersion(entity.isLastVersion())
                .withOriginalDatasetId(entity.getOriginalDatasetId())
                .build();
    }

    // strange but on purpose to get back each type in one for pagination in order to use abstract
    public static ConversationDataset mapForAbstract(final InstantMessageDatasetEntity entity){
        if(Objects.isNull(entity)){
            return null;
        }
        return ConversationDataset.newBuilder()
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
                .withOngoingGenerationId(Optional.ofNullable(entity.getOngoingGeneration()).map(BaseUuidEntity::getId).orElse(null))
                .withStatistic(StatisticJpaToDomainMapper.map(entity.getStatistic()))
                .withValidated(entity.isValidated())
                .withLastVersion(entity.isLastVersion())
                .withOriginalDatasetId(entity.getOriginalDatasetId())
                .build();
    }
}
