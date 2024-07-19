package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageDataSetDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageDatasetJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaInstantMessageDatasetRepository implements InstantMessageDatasetRepository{

    private final JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaInstantMessageDatasetRepository(JpaInstantMessageDatasetRepositoryCRUD jpaInstantMessageDatasetRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaInstantMessageDatasetRepositoryCRUD = jpaInstantMessageDatasetRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public InstantMessageDataset saveInstantMessageDataset(InstantMessageDataset instantMessageDataset) {
        final AuthorEntity author = jpaAuthorRepositoryCRUD.getReferenceById(instantMessageDataset.getAuthor().getId());

        return InstantMessageDatasetJpaToDomainMapper.map(jpaInstantMessageDatasetRepositoryCRUD.save(InstantMessageDataSetDomainToJpaMapper
                .mapForCreate(instantMessageDataset, author)));
    }

    @Override
    public InstantMessageDataset getInstantMessageDatasetById(UUID instantMessageDatasetId) {
        return InstantMessageDatasetJpaToDomainMapper.map(jpaInstantMessageDatasetRepositoryCRUD.getReferenceById(instantMessageDatasetId));
    }

    @Override
    public void deleteInstantMessageDatasetById(UUID instantMessageDatasetId) {
        jpaInstantMessageDatasetRepositoryCRUD.deleteById(instantMessageDatasetId);
    }
}
