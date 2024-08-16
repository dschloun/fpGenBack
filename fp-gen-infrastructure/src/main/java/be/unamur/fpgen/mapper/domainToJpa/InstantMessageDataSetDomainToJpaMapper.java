package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;

import java.util.Set;

public class InstantMessageDataSetDomainToJpaMapper {

    public static InstantMessageDatasetEntity mapForCreate(final InstantMessageDataset domain, final AuthorEntity author){
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
        return entity;
    }

    public static InstantMessageDatasetEntity mapForCreateNewVersion(final InstantMessageDatasetEntity oldVersionEntity, final Set<InstantMessageGenerationEntity> generations, final AuthorEntity author, final InstantMessageDataset newVersion){
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
        return entity;
    }

    public static InstantMessageDatasetEntity mapForCreate(final InstantMessageDataset domain, final AuthorEntity author, final ProjectEntity project){
        final InstantMessageDatasetEntity entity = new InstantMessageDatasetEntity();
        entity.setProject(project);
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
        return entity;
    }

    public static InstantMessageDatasetEntity mapForUpdate(final InstantMessageDatasetEntity entity, final InstantMessageDataset domain){
        entity.setValidated(domain.isValidated());
        entity.setLastVersion(domain.isLastVersion());
        return entity;
    }
}
