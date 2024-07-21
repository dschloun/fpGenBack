package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageDataSetDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageDatasetJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.*;

@Repository
public class JpaInstantMessageDatasetRepository implements InstantMessageDatasetRepository{

    private final JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;

    public JpaInstantMessageDatasetRepository(JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD, JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD) {
        this.jpaInstantMessageDatasetRepositoryCRUD = jpaInstantMessageDatasetRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
    }

    @Override
    public InstantMessageDataset saveInstantMessageDataset(InstantMessageDataset instantMessageDataset) {
        final AuthorEntity author = jpaAuthorRepositoryCRUD.getReferenceById(instantMessageDataset.getAuthor().getId());

        return InstantMessageDatasetJpaToDomainMapper.mapInstantMessageDataset(jpaInstantMessageDatasetRepositoryCRUD.save(InstantMessageDataSetDomainToJpaMapper
                .mapForCreate(instantMessageDataset, author)));
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
        final HashSet<InstantMessageGenerationEntity> instantMessageGenerations = new HashSet<>();
        generations.forEach(g ->{
            instantMessageGenerations.add(jpaInstantMessageGenerationRepositoryCRUD.getReferenceById(g.getId()));
        });
        dataset.getInstantMessageGenerationList().addAll(instantMessageGenerations);
        jpaInstantMessageDatasetRepositoryCRUD.save(dataset);
    }

    @Override
    public DatasetsPage findPagination(String version, String name, String description, String comment, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        // 1. get in Page format
        Page<ConversationDataset> page = jpaInstantMessageDatasetRepositoryCRUD.findPagination(
                name,
                version,
                authorTrigram,
                description,
                comment,
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
}
