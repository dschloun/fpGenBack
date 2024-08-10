package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.utils.MapperUtil;

import java.util.Optional;

public class ConversationDatasetJpaToDomainMapper {

    public static ConversationDataset map(final ConversationDatasetEntity entity){
        if(entity == null){
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
                .withConversationGenerationList(MapperUtil.mapSet(entity.getConversationGenerationList(), ConversationGenerationJpaToDomainMapper::map))
                .withOngoingGenerationId(Optional.ofNullable(entity.getOngoingGeneration()).map(BaseUuidEntity::getId).orElse(null))
                .build();
    }
}
