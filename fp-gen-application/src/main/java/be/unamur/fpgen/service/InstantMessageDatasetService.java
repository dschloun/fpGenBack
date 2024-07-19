package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.mapper.webToDomain.DatasetWebToDomainMapper;
import be.unamur.fpgen.repository.InstantMessageDatasetRepository;
import be.unamur.model.DatasetCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class InstantMessageDatasetService {
    private final AuthorService authorService;
    private final InstantMessageDatasetRepository instantMessageDatasetRepository;

    public InstantMessageDatasetService(AuthorService authorService, InstantMessageDatasetRepository instantMessageDatasetRepository) {
        this.authorService = authorService;
        this.instantMessageDatasetRepository = instantMessageDatasetRepository;
    }

    @Transactional
    public InstantMessageDataset createDatasetSet(DatasetCreation datasetCreation){
        final Author author = authorService.getAuthorById(datasetCreation.getAuthorId());
        return instantMessageDatasetRepository.saveInstantMessageDataset(
                InstantMessageDataset.newBuilder()
                        .withAuthor(author)
                        .withDatasetFunction(DatasetWebToDomainMapper.mapFunction(datasetCreation.getType()))
                        .withComment(datasetCreation.getComment())
                        .withDescription(datasetCreation.getDescription())
                        .withVersion(datasetCreation.getVersion())
                        .build());
    }
}
