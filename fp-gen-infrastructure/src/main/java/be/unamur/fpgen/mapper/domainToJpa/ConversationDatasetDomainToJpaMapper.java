package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;

public class ConversationDatasetDomainToJpaMapper {

    public static ConversationDatasetEntity mapForCreate(final ConversationDataset domain, final AuthorEntity author){
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
        return entity;
    }

    public static ConversationDatasetEntity mapForCreate(final ConversationDataset domain, final AuthorEntity author, final ProjectEntity project){
        ConversationDatasetEntity entity = new ConversationDatasetEntity();
        entity.setProject(project);
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
        return entity;
    }
}
