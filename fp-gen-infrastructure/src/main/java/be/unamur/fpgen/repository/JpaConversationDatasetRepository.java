package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.mapper.domainToJpa.ConversationDatasetDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationDatasetJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaConversationDatasetRepository implements ConversationDatasetRepository{
    private final JpaConversationDatasetRepositoryCRUD jpaConversationDatasetRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaConversationDatasetRepository(JpaConversationDatasetRepositoryCRUD jpaConversationDatasetRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaConversationDatasetRepositoryCRUD = jpaConversationDatasetRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public ConversationDataset saveConversationDataset(ConversationDataset conversationDataset) {
        final AuthorEntity author = jpaAuthorRepositoryCRUD.getReferenceById(conversationDataset.getAuthor().getId());

        return ConversationDatasetJpaToDomainMapper.map(jpaConversationDatasetRepositoryCRUD.save(ConversationDatasetDomainToJpaMapper
                .mapForCreate(conversationDataset, author)));
    }

    @Override
    public ConversationDataset getConversationDatasetById(UUID conversationDatasetId) {
        return ConversationDatasetJpaToDomainMapper.map(jpaConversationDatasetRepositoryCRUD.getReferenceById(conversationDatasetId));
    }

    @Override
    public void deleteConversationDatasetById(UUID conversationDatasetId) {
        jpaConversationDatasetRepositoryCRUD.deleteById(conversationDatasetId);
    }
}
