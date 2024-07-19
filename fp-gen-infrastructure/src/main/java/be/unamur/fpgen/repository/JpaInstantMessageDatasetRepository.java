package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageDataSetDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageDatasetJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaInstantMessageDatasetRepository implements InstantMessageDatasetRepository{

    private final JpaInstantMessageDatasetRepositoryCRUD instantMessageDatasetRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD authorRepositoryCRUD;

    public JpaInstantMessageDatasetRepository(JpaInstantMessageDatasetRepositoryCRUD instantMessageDatasetRepositoryCRUD, JpaAuthorRepositoryCRUD authorRepositoryCRUD) {
        this.instantMessageDatasetRepositoryCRUD = instantMessageDatasetRepositoryCRUD;
        this.authorRepositoryCRUD = authorRepositoryCRUD;
    }

    @Override
    public InstantMessageDataset saveInstantMessageDataset(InstantMessageDataset instantMessageDataset) {
        final AuthorEntity author = authorRepositoryCRUD.getReferenceById(instantMessageDataset.getAuthor().getId());

        return InstantMessageDatasetJpaToDomainMapper.map(instantMessageDatasetRepositoryCRUD.save(InstantMessageDataSetDomainToJpaMapper
                .mapForCreate(instantMessageDataset, author)));
    }

    @Override
    public InstantMessageDataset getInstantMessageDatasetById(UUID instantMessageDatasetId) {
        return InstantMessageDatasetJpaToDomainMapper.map(instantMessageDatasetRepositoryCRUD.getReferenceById(instantMessageDatasetId));
    }

    @Override
    public void deleteInstantMessageDatasetById(UUID instantMessageDatasetId) {
        instantMessageDatasetRepositoryCRUD.deleteById(instantMessageDatasetId);
    }
}
