package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;

import java.util.Set;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class DataSetDomainToJpaMapper {

    public static DatasetEntity mapForCreate(final Dataset domain, final AuthorEntity author, final ProjectEntity project){
        if (DatasetTypeEnum.INSTANT_MESSAGE.equals(domain.getType())){
            return mapForCreateMessageDataset( domain, author, project);
        } else {
            return mapForCreateConversationDataset(domain, author, project);
        }
    }

    private static InstantMessageDatasetEntity mapForCreateMessageDataset(final Dataset domain, final AuthorEntity author, final ProjectEntity project){
        final InstantMessageDatasetEntity entity = new InstantMessageDatasetEntity();
        entity.setBusinessId(domain.getBusinessId());
        entity.setVersion(domain.getVersion());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setComment(domain.getComment());
        entity.setFunction(domain.getDatasetFunction());
        entity.setAuthor(author);
        entity.setValidated(domain.isValidated());
        entity.setLastVersion(domain.isLastVersion());
        entity.setOriginalDatasetId(domain.getOriginalDatasetId());
        entity.setProject(project);
        return entity;
    }

    private static ConversationDatasetEntity mapForCreateConversationDataset(final Dataset domain, final AuthorEntity author, final ProjectEntity project){
        ConversationDatasetEntity entity = new ConversationDatasetEntity();
        entity.setAuthor(author);
        entity.setBusinessId(domain.getBusinessId());
        entity.setComment(domain.getComment());
        entity.setDescription(domain.getDescription());
        entity.setName(domain.getName());
        entity.setVersion(domain.getVersion());
        entity.setFunction(domain.getDatasetFunction());
        entity.setValidated(domain.isValidated());
        entity.setLastVersion(domain.isLastVersion());
        entity.setOriginalDatasetId(domain.getOriginalDatasetId());
        entity.setProject(project);
        return entity;
    }


    public static InstantMessageDatasetEntity mapForCreateNewVersionMessageDataset(final InstantMessageDatasetEntity oldVersionEntity, final Set<InstantMessageGenerationEntity> generations, final AuthorEntity author, final Dataset newVersion){
        final InstantMessageDatasetEntity entity = new InstantMessageDatasetEntity();
        entity.setBusinessId(newVersion.getBusinessId());
        entity.setVersion(newVersion.getVersion());
        entity.setName(newVersion.getName());
        entity.setDescription(oldVersionEntity.getDescription());
        entity.setComment(oldVersionEntity.getComment());
        entity.setFunction(oldVersionEntity.getFunction());
        entity.setAuthor(author);
        entity.setInstantMessageGenerationList(generations);
        entity.setValidated(newVersion.isValidated());
        entity.setLastVersion(newVersion.isLastVersion());
        entity.setOriginalDatasetId(newVersion.getOriginalDatasetId());
        entity.setProject(oldVersionEntity.getProject());
        entity.setStatistic(StatisticDomainToJpaMapper.mapForCreate(newVersion.getStatistic(), entity));
        return entity;
    }

    public static ConversationDatasetEntity mapForCreateNewConversationDataset(final ConversationDatasetEntity oldVersionEntity, final Set<ConversationGenerationEntity> generations, final AuthorEntity author, final Dataset newVersion){
        final ConversationDatasetEntity entity = new ConversationDatasetEntity();
        entity.setBusinessId(newVersion.getBusinessId());
        entity.setVersion(newVersion.getVersion());
        entity.setName(newVersion.getName());
        entity.setDescription(oldVersionEntity.getDescription());
        entity.setComment(oldVersionEntity.getComment());
        entity.setFunction(oldVersionEntity.getFunction());
        entity.setAuthor(author);
        entity.setConversationGenerationList(generations);
        entity.setValidated(newVersion.isValidated());
        entity.setLastVersion(newVersion.isLastVersion());
        entity.setOriginalDatasetId(newVersion.getOriginalDatasetId());
        entity.setProject(oldVersionEntity.getProject());
        entity.setStatistic(StatisticDomainToJpaMapper.mapForCreate(newVersion.getStatistic(), entity));
        return entity;
    }

    public static DatasetEntity mapForUpdate(final DatasetEntity entity, final Dataset domain){
        entity.setValidated(domain.isValidated());
        entity.setLastVersion(domain.isLastVersion());
        return entity;
    }
}
