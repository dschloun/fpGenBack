package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.mapper.webToDomain.DatasetWebToDomainMapper;
import be.unamur.fpgen.repository.InstantMessageDatasetRepository;
import be.unamur.model.DatasetCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class InstantMessageDatasetService {
    private final AuthorService authorService;
    private final InstantMessageDatasetRepository instantMessageDatasetRepository;

    public InstantMessageDatasetService(AuthorService authorService, InstantMessageDatasetRepository instantMessageDatasetRepository) {
        this.authorService = authorService;
        this.instantMessageDatasetRepository = instantMessageDatasetRepository;
    }

    @Transactional
    public InstantMessageDataset createDataset(DatasetCreation datasetCreation){
        final Author author = authorService.getAuthorById(datasetCreation.getAuthorId());
        return instantMessageDatasetRepository.saveInstantMessageDataset(
                InstantMessageDataset.newBuilder()
                        .withAuthor(author)
                        .withDatasetFunction(DatasetWebToDomainMapper.mapFunction(datasetCreation.getFunction()))
                        .withComment(datasetCreation.getComment())
                        .withDescription(datasetCreation.getDescription())
                        .withVersion(datasetCreation.getVersion())
                        .withName(datasetCreation.getName())
                        .build());
    }

    @Transactional
    public InstantMessageDataset getDatasetById(UUID datasetId){
        return instantMessageDatasetRepository.getInstantMessageDatasetById(datasetId);
    }

    @Transactional
    public void deleteDatasetById(UUID datasetId){
        instantMessageDatasetRepository.deleteInstantMessageDatasetById(datasetId);
    }
}
