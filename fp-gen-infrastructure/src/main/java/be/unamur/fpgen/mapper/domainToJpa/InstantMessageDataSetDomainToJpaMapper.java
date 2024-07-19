package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;

import java.util.Set;

public class InstantMessageDataSetDomainToJpaMapper {

    public static InstantMessageDatasetEntity mapForCreate(final InstantMessageDataset domain, final AuthorEntity author){
        final InstantMessageDatasetEntity entity = new InstantMessageDatasetEntity();
        entity.setBusinessId(domain.getBusinessId());
        entity.setVersion(domain.getVersion());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setComment(domain.getComment());
        entity.setAuthor(AuthorDomainToJpaMapper.mapForCreate(domain.getAuthor()));
        entity.setFunction(domain.getDatasetFunction());
        entity.setAuthor(author);
        return entity;
    }

    public static InstantMessageDatasetEntity mapForUpdate(final InstantMessageDatasetEntity entity, final Set<InstantMessageGenerationEntity> generationList){
        entity.getInstantMessageGenerationList().addAll(generationList);
        return entity;
    }
}
