package be.unamur.fpgen.repository.result;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.mapper.domainToJpa.ResultDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ResultJpaToDomainMapper;
import be.unamur.fpgen.repository.ResultRepository;
import be.unamur.fpgen.repository.author.JpaAuthorRepositoryCRUD;
import be.unamur.fpgen.repository.dataset.JpaDatasetRepositoryCRUD;
import be.unamur.fpgen.result.Result;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaResultRepository implements ResultRepository {

    private final JpaDatasetRepositoryCRUD jpaDatasetRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;
    private final JpaResultRepositoryCRUD jpaResultRepositoryCRUD;

    public JpaResultRepository(JpaDatasetRepositoryCRUD jpaDatasetRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD, JpaResultRepositoryCRUD jpaResultRepositoryCRUD) {
        this.jpaDatasetRepositoryCRUD = jpaDatasetRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
        this.jpaResultRepositoryCRUD = jpaResultRepositoryCRUD;
    }

    @Override
    public Result saveResult(Dataset dataset, Result result) {
        final DatasetEntity datasetEntity = jpaDatasetRepositoryCRUD.getReferenceById(dataset.getId());
        final AuthorEntity authorEntity = jpaAuthorRepositoryCRUD.getReferenceById(result.getAuthor().getId());
        return ResultJpaToDomainMapper.map(jpaResultRepositoryCRUD.save(ResultDomainToJpaMapper.map(result, datasetEntity, authorEntity)));
    }

    @Override
    public Optional<Result> findResultById(UUID resultId) {
        return jpaResultRepositoryCRUD.findById(resultId).map(ResultJpaToDomainMapper::map);
    }

    @Override
    public Result updateResult(Result result) {
        final DatasetEntity datasetEntity = jpaDatasetRepositoryCRUD.getReferenceById(result.getDataset().getId());
        final AuthorEntity authorEntity = jpaAuthorRepositoryCRUD.getReferenceById(result.getAuthor().getId());
        return ResultJpaToDomainMapper.map(jpaResultRepositoryCRUD.save(ResultDomainToJpaMapper.map(result, datasetEntity, authorEntity)));
    }

    @Override
    public void DeleteResult(UUID resultId) {
        jpaResultRepositoryCRUD.deleteById(resultId);
    }

    @Override
    public List<Result> findAllResultByDatasetId(UUID datasetId) {
        return jpaResultRepositoryCRUD.findAllByDatasetIdOrderByCreationDateDesc(datasetId)
                .stream()
                .map(ResultJpaToDomainMapper::map)
                .toList();
    }
}
