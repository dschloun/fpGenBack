package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.mapper.domainToJpa.ConversationDatasetDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationDatasetJpaToDomainMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationDatasetJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
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

    @Override
    public void removeConversationListFromDataset(ConversationDataset datasetIn, Set<ConversationGeneration> generations) {
        final ConversationDatasetEntity dataset = jpaConversationDatasetRepositoryCRUD.getReferenceById(datasetIn.getId());
        dataset.getConversationGenerationList().removeAll(getGenerationList(generations));
        jpaConversationDatasetRepositoryCRUD.save(dataset);
    }

    @Override
    public DatasetsPage findPagination(String version, String name, String description, String comment, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        // 1. get in Page format
        Page<ConversationDataset> page = jpaConversationDatasetRepositoryCRUD.findPagination(
                StringUtil.toLowerCaseIfNotNull(name),
                StringUtil.toLowerCaseIfNotNull(version),
                authorTrigram,
                StringUtil.toLowerCaseIfNotNull(description),
                StringUtil.toLowerCaseIfNotNull(comment),
                startDate,
                endDate,
                pageable
        ).map(ConversationDatasetJpaToDomainMapper::map);

        final DatasetsPage datasetsPage = DatasetsPage.newBuilder()
                .withPagination(new Pagination.Builder()
                        .size(page.getSize())
                        .totalSize((int) page.getTotalElements())
                        .page(page.getNumber())
                        .build())
                .withDatasetList(page.getContent())
                .build();

        return datasetsPage;
    }

    private Set<ConversationGenerationEntity> getGenerationList(Set<ConversationGeneration> generations){
        final HashSet<ConversationGenerationEntity> conversationGenerations = new HashSet<>();
        generations.forEach(g ->{
            conversationGenerations.add(jpaConversationGenerationRepositoryCRUD.getReferenceById(g.getId()));
        });
        return conversationGenerations;
    }
}
