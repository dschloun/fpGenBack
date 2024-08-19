package be.unamur.fpgen.repository.dataset;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.mapper.domainToJpa.DataSetDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.DatasetJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.repository.DatasetRepository;
import be.unamur.fpgen.repository.author.JpaAuthorRepositoryCRUD;
import be.unamur.fpgen.repository.generation.JpaGenerationRepositoryCRUD;
import be.unamur.fpgen.repository.ongoing.JpaOngoingGenerationRepositoryCRUD;
import be.unamur.fpgen.repository.generation.JpaInstantMessageGenerationRepositoryCRUD;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.fpgen.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public class JpaDatasetRepository implements DatasetRepository {

    private final JpaDatasetRepositoryCRUD jpaDatasetRepositoryCRUD;
    private final JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;
    private final JpaGenerationRepositoryCRUD jpaGenerationRepositoryCRUD;
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;
    private final JpaOngoingGenerationRepositoryCRUD jpaOngoingGenerationRepositoryCRUD;
    private final EntityManager entityManager;

    public JpaDatasetRepository(JpaDatasetRepositoryCRUD jpaDatasetRepositoryCRUD, JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD, JpaGenerationRepositoryCRUD jpaGenerationRepositoryCRUD, JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD, JpaOngoingGenerationRepositoryCRUD jpaOngoingGenerationRepositoryCRUD, EntityManager entityManager) {
        this.jpaDatasetRepositoryCRUD = jpaDatasetRepositoryCRUD;
        this.jpaInstantMessageDatasetRepositoryCRUD = jpaInstantMessageDatasetRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
        this.jpaGenerationRepositoryCRUD = jpaGenerationRepositoryCRUD;
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
        this.jpaOngoingGenerationRepositoryCRUD = jpaOngoingGenerationRepositoryCRUD;
        this.entityManager = entityManager;
    }

    @Override
    public Dataset saveDataset(Dataset dataset) {
        final AuthorEntity author = jpaAuthorRepositoryCRUD.getReferenceById(dataset.getAuthor().getId());

        return DatasetJpaToDomainMapper.map(jpaDatasetRepositoryCRUD.save(DataSetDomainToJpaMapper
                .mapForCreate(dataset, author, null)));
    }

    @Override
    public Dataset saveNewVersion(Dataset oldVersion, Dataset newVersion) {
        // maybe it's not the old version author
        final AuthorEntity author = jpaAuthorRepositoryCRUD.getReferenceById(newVersion.getAuthor().getId());

        if(DatasetTypeEnum.INSTANT_MESSAGE.equals(oldVersion.getType())) {
            return createInstantMessageDatasetNewVersion(oldVersion, newVersion, author);
        } else {
            //todo
        }
        return createInstantMessageDatasetNewVersion(oldVersion, newVersion, author); // remove when both are made
    }

    private Dataset createInstantMessageDatasetNewVersion(Dataset oldVersion, Dataset newVersion, AuthorEntity author) {
        final InstantMessageDatasetEntity entity = jpaInstantMessageDatasetRepositoryCRUD.findById(oldVersion.getId()).orElseThrow();

        // detach old generations in order to copy them
        final Set<InstantMessageGenerationEntity> detachedGenerations = new HashSet<>();
        for (InstantMessageGenerationEntity generation : entity.getInstantMessageGenerationList()) {
            entityManager.detach(generation);
            detachedGenerations.add(generation);
        }

        return DatasetJpaToDomainMapper.map(
                jpaDatasetRepositoryCRUD.save(
                        DataSetDomainToJpaMapper
                                .mapForCreateNewVersionMessageDataset(entity, detachedGenerations, author, newVersion)));
    }


    @Override
    public Dataset updateDataset(Dataset dataset) {
        final DatasetEntity entity = jpaDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
        return DatasetJpaToDomainMapper.map(jpaDatasetRepositoryCRUD.save(DataSetDomainToJpaMapper
                .mapForUpdate(entity, dataset)));
    }

    @Override
    public Optional<Dataset> findDatasetById(UUID datasetId) {
        return jpaDatasetRepositoryCRUD.findById(datasetId)
                .map(DatasetJpaToDomainMapper::map);
    }

    @Override
    public boolean isProjectDataset(UUID datasetId) {
        final DatasetEntity datasetEntity = jpaDatasetRepositoryCRUD.findById(datasetId).orElseThrow();
        return Objects.nonNull(datasetEntity.getProject());
    }

    @Override
    public Optional<Dataset> findDatasetByOriginalDatasetAndVersion(UUID originalDatasetId, Integer version) {
        if(version.equals(0)){
            return jpaDatasetRepositoryCRUD.findById(originalDatasetId)
                    .map(DatasetJpaToDomainMapper::map);
        }
        return jpaDatasetRepositoryCRUD.findByOriginalDatasetIdAndVersion(originalDatasetId, version)
                .map(DatasetJpaToDomainMapper::map);
    }

    @Override
    public List<Dataset> findAllDatasetVersions(UUID origineDatasetId) {
        return MapperUtil.mapList(jpaDatasetRepositoryCRUD
                        .findAllByIdOrOriginalDatasetIdOrderByVersionDesc(origineDatasetId, origineDatasetId),
                DatasetJpaToDomainMapper::map);
    }

    @Override
    public void deleteDatasetById(UUID datasetId) {
        jpaDatasetRepositoryCRUD.deleteById(datasetId);
    }

    @Override
    public void addItemListToDataset(Dataset datasetId, Set<Generation> generations) {
        if(DatasetTypeEnum.INSTANT_MESSAGE.equals(datasetId.getType())) {
            addInstantMessageGenerationListToDataset(datasetId, generations);
        }
    }



    @Override
    public void removeItemListFromDataset(Dataset dataset, Set<Generation> generations) {
        removeInstantMessageGenerationListFromDataset(dataset, generations);
    }

    @Override
    public DatasetsPage findPagination(DatasetTypeEnum type, Integer version, String name, String description, String comment, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        // 1. get in Page format
        final Page<Dataset> page;

        if(DatasetTypeEnum.INSTANT_MESSAGE.equals(type)) {
            page = jpaDatasetRepositoryCRUD.findInstantMessagePagination(
                    StringUtil.toLowerCaseIfNotNull(name),
                    version,
                    authorTrigram,
                    StringUtil.toLowerCaseIfNotNull(description),
                    StringUtil.toLowerCaseIfNotNull(comment),
                    startDate,
                    endDate,
                    pageable
            ).map(DatasetJpaToDomainMapper::map);
        } else {
            page = jpaDatasetRepositoryCRUD.findConversationPagination(
                    StringUtil.toLowerCaseIfNotNull(name),
                    version,
                    authorTrigram,
                    StringUtil.toLowerCaseIfNotNull(description),
                    StringUtil.toLowerCaseIfNotNull(comment),
                    startDate,
                    endDate,
                    pageable
            ).map(DatasetJpaToDomainMapper::map);
        }

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
    public void addOngoingGenerationToDataset(Dataset dataset, OngoingGeneration generation) {
        final OngoingGenerationEntity ongoingGeneration = jpaOngoingGenerationRepositoryCRUD.getReferenceById(generation.getId());
        final DatasetEntity datasetEntity = jpaDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
        datasetEntity.setOngoingGeneration(ongoingGeneration);
        jpaDatasetRepositoryCRUD.save(datasetEntity);
    }

    @Override
    public void removeOngoingGenerationToDataset(Dataset dataset, OngoingGeneration generation) {
        final DatasetEntity datasetEntity = jpaDatasetRepositoryCRUD.findById(dataset.getId()).orElseThrow();
        datasetEntity.setOngoingGeneration(null);
        jpaDatasetRepositoryCRUD.save(datasetEntity);
    }


    // separate, no choice
    private Set<InstantMessageGenerationEntity> getInstantMessageGenerationList(Set<Generation> generations) {
        final HashSet<InstantMessageGenerationEntity> instantMessageGenerations = new HashSet<>();
        generations.forEach(g -> {
            instantMessageGenerations.add(jpaInstantMessageGenerationRepositoryCRUD.getReferenceById(g.getId()));
        });
        return instantMessageGenerations;
    }

    private void addInstantMessageGenerationListToDataset(Dataset datasetId, Set<Generation> generations) {
        final InstantMessageDatasetEntity dataset = jpaInstantMessageDatasetRepositoryCRUD.getReferenceById(datasetId.getId());
        dataset.getInstantMessageGenerationList().addAll(getInstantMessageGenerationList(generations));
        jpaDatasetRepositoryCRUD.save(dataset);
    }

    private void removeInstantMessageGenerationListFromDataset(Dataset dataset, Set<Generation> generations) {
        final InstantMessageDatasetEntity datasetEntity = jpaInstantMessageDatasetRepositoryCRUD.getReferenceById(dataset.getId());
        datasetEntity.getInstantMessageGenerationList().removeAll(getInstantMessageGenerationList(generations));
        jpaDatasetRepositoryCRUD.save(datasetEntity);
    }
}
