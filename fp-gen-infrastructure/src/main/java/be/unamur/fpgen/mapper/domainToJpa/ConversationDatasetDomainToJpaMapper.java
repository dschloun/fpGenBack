package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;

public class ConversationDatasetDomainToJpaMapper {

    public static ConversationDatasetEntity mapForCreate(final ConversationDataset domain, final AuthorEntity author){
        ConversationDatasetEntity entity = new ConversationDatasetEntity();
        entity.setAuthor(author);
        entity.setBusinessId(domain.getBusinessId());
        entity.setComment(domain.getComment());
        entity.setDescription(domain.getDescription());
        entity.setName(domain.getName());
        entity.setVersion(domain.getVersion());
        return entity;
    }
}
