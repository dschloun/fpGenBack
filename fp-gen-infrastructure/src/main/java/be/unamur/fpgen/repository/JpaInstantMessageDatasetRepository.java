package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageDataSetDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageDatasetJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public class JpaInstantMessageDatasetRepository implements InstantMessageDatasetRepository {

    private final JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;
    private final JpaOngoingGenerationRepositoryCRUD jpaOngoingGenerationRepositoryCRUD;
    private final EntityManager entityManager;

    public JpaInstantMessageDatasetRepository(JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD, JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD, JpaOngoingGenerationRepositoryCRUD jpaOngoingGenerationRepositoryCRUD, EntityManager entityManager) {
        this.jpaInstantMessageDatasetRepositoryCRUD = jpaInstantMessageDatasetRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
        this.jpaOngoingGenerationRepositoryCRUD = jpaOngoingGenerationRepositoryCRUD;
        this.entityManager = entityManager;
    }

    @Override
    public InstantMessageDataset saveInstantMessageDataset(InstantMessageDataset instantMessageDataset) {
        final AuthorEntity author = jpaAuthorRepositoryCRUD.getReferenceById(instantMessageDataset.getAuthor().getId());

        return InstantMessageDatasetJpaToDomainMapper.mapInstantMessageDataset(jpaInstantMessageDatasetRepositoryCRUD.save(InstantMessageDataSetDomainToJpaMapper
                .mapForCreate(instantMessageDataset, author)));
    }

    @Override
    public InstantMessageDataset saveNewVersion(InstantMessageDataset oldVersion, InstantMessageDataset newVersion) {
        // maybe it's not the old version author
        final AuthorEntity author = jpaAuthorRepositoryCRUD.getReferenceById(oldVersion.getAuthor().getId());

        final InstantMessageDatasetEntity entity = jpaInstantMessageDatasetRepositoryCRUD.findById(oldVersion.getId()).orElseThrow();

        // detach old generations in order to copy them
        final Set<InstantMessageGenerationEntity> detachedGenerations = new HashSet<>();
        for (InstantMessageGenerationEntity generation : entity.getInstantMessageGenerationList()) {
            entityManager.detach(generation);
            detachedGenerations.add(generation);
        }

        return InstantMessageDatasetJpaToDomainMapper.mapInstantMessageDataset(
                jpaInstantMessageDatasetRepositoryCRUD.save(
                        InstantMessageDataSetDomainToJpaMapper
                                .mapForCreateNewVersion(entity, detachedGenerations, author, newVersion)));
    }

    @Override
    public InstantMessageDataset updateInstantMessageDataset(InstantMessageDataset instantMessageDataset) {
        final InstantMessageDatasetEntity entity = jpaInstantMessageDatasetRepositoryCRUD.findById(instantMessageDataset.getId()).orElseThrow();
        return InstantMessageDatasetJpaToDomainMapper.mapInstantMessageDataset(jpaInstantMessageDatasetRepositoryCRUD.save(InstantMessageDataSetDomainToJpaMapper
                .mapForUpdate(entity, instantMessageDataset)));
    }

    @Override
    public Optional<InstantMessageDataset> findInstantMessageDatasetById(UUID instantMessageDatasetId) {
        return jpaInstantMessageDatasetRepositoryCRUD.findById(instantMessageDatasetId)
                .map(InstantMessageDatasetJpaToDomainMapper::mapInstantMessageDataset);
    }

    @Override
    public void deleteInstantMessageDatasetById(UUID instantMessageDatasetId) {
        jpaInstantMessageDatasetRepositoryCRUD.deleteById(instantMessageDatasetId);
    }

    @Override
    public void addInstantMessageListToDataset(InstantMessageDataset datasetId, Set<InstantMessageGeneration> generations) {
        final InstantMessageDatasetEntity dataset = jpaInstantMessageDatasetRepositoryCRUD.getReferenceById(datasetId.getId());
        dataset.getInstantMessageGenerationList().addAll(getGenerationList(generations));
        jpaInstantMessageDatasetRepositoryCRUD.save(dataset);
    }

    @Override
    public void removeInstantMessageListFromDataset(InstantMessageDataset dataset, Set<InstantMessageGeneration> generations) {
        final InstantMessageDatasetEntity datasetEntity = jpaInstantMessageDatasetRepositoryCRUD.getReferenceById(dataset.getId());
        datasetEntity.getInstantMessageGenerationList().removeAll(getGenerationList(generations));
        jpaInstantMessageDatasetRepositoryCRUD.save(datasetEntity);
    }

    @Override
    public DatasetsPage findPagination(String version, String name, String description, String comment, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        // 1. get in Page format
        Page<ConversationDataset> page = jpaInstantMessageDatasetRepositoryCRUD.findPagination(
                StringUtil.toLowerCaseIfNotNull(name),
                StringUtil.toLowerCaseIfNotNull(version),
                authorTrigram,
                StringUtil.toLowerCaseIfNotNull(description),
                StringUtil.toLowerCaseIfNotNull(comment),
                startDate,
                endDate,
                pageable
        ).map(InstantMessageDatasetJpaToDomainMapper::mapForAbstract);

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

    @Override
    public void addOngoingGenerationToDataset(InstantMessageDataset dataset, OngoingGeneration generation) {
        final OngoingGenerationEntity ongoingGeneration = jpaOngoingGenerationRepositoryCRUD.getReferenceById(generation.getId());
        final InstantMessageDatasetEntity datasetEntity = jpaInstantMessageDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
        datasetEntity.setOngoingGeneration(ongoingGeneration);
        jpaInstantMessageDatasetRepositoryCRUD.save(datasetEntity);
    }

    @Override
    public void removeOngoingGenerationToDataset(InstantMessageDataset dataset, OngoingGeneration generation) {
        final InstantMessageDatasetEntity datasetEntity = jpaInstantMessageDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
        datasetEntity.setOngoingGeneration(null);
        jpaInstantMessageDatasetRepositoryCRUD.save(datasetEntity);
    }

    private Set<InstantMessageGenerationEntity> getGenerationList(Set<InstantMessageGeneration> generations) {
        final HashSet<InstantMessageGenerationEntity> instantMessageGenerations = new HashSet<>();
        generations.forEach(g -> {
            instantMessageGenerations.add(jpaInstantMessageGenerationRepositoryCRUD.getReferenceById(g.getId()));
        });
        return instantMessageGenerations;
    }
}
