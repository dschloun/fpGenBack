package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageDataSetDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageDatasetJpaToDomainMapper;
import org.springframework.stereotype.Repository;

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

        return InstantMessageDatasetJpaToDomainMapper.map(jpaInstantMessageDatasetRepositoryCRUD.save(InstantMessageDataSetDomainToJpaMapper
                .mapForCreate(instantMessageDataset, author)));
    }

    @Override
    public Optional<InstantMessageDataset> findInstantMessageDatasetById(UUID instantMessageDatasetId) {
        return jpaInstantMessageDatasetRepositoryCRUD.findById(instantMessageDatasetId)
                .map(InstantMessageDatasetJpaToDomainMapper::map);
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
}
