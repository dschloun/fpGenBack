package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.mapper.domainToJpa.ConversationDatasetDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationDatasetJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class JpaConversationDatasetRepository implements ConversationDatasetRepository{
    private final JpaConversationDatasetRepositoryCRUD jpaConversationDatasetRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;
    private final JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD;

    public JpaConversationDatasetRepository(JpaConversationDatasetRepositoryCRUD jpaConversationDatasetRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD, JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD) {
        this.jpaConversationDatasetRepositoryCRUD = jpaConversationDatasetRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
        this.jpaConversationGenerationRepositoryCRUD = jpaConversationGenerationRepositoryCRUD;
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

    @Override
    public void addConversationListToDataset(ConversationDataset datasetIn, Set<ConversationGeneration> generations) {
        final ConversationDatasetEntity dataset = jpaConversationDatasetRepositoryCRUD.getReferenceById(datasetIn.getId());
        final HashSet<ConversationGenerationEntity> ConversationGenerations = new HashSet<>();
        generations.forEach(g ->{
            ConversationGenerations.add(jpaConversationGenerationRepositoryCRUD.getReferenceById(g.getId()));
        });
        dataset.getConversationGenerationList().addAll(ConversationGenerations);
        jpaConversationDatasetRepositoryCRUD.save(dataset);
    }
}
