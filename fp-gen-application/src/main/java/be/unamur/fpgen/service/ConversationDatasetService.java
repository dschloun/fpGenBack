package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.mapper.webToDomain.DatasetWebToDomainMapper;
import be.unamur.fpgen.repository.ConversationDatasetRepository;
import be.unamur.model.DatasetCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ConversationDatasetService {
    private final AuthorService authorService;
    private final ConversationDatasetRepository conversationDatasetRepository;

    public ConversationDatasetService(AuthorService authorService, ConversationDatasetRepository conversationDatasetRepository) {
        this.authorService = authorService;
        this.conversationDatasetRepository = conversationDatasetRepository;
    }

    @Transactional
    public ConversationDataset createDataset(DatasetCreation datasetCreation){
        final Author author = authorService.getAuthorById(datasetCreation.getAuthorId());
        return conversationDatasetRepository.saveConversationDataset(
                ConversationDataset.newBuilder()
                        .withAuthor(author)
                        .withDatasetFunction(DatasetWebToDomainMapper.mapFunction(datasetCreation.getFunction()))
                        .withComment(datasetCreation.getComment())
                        .withDescription(datasetCreation.getDescription())
                        .withVersion(datasetCreation.getVersion())
                        .withName(datasetCreation.getName())
                        .build());
    }

    @Transactional
    public ConversationDataset getDatasetById(UUID datasetId){
        return conversationDatasetRepository.getConversationDatasetById(datasetId);
    }

    @Transactional
    public void deleteDatasetById(UUID datasetId){
        conversationDatasetRepository.deleteConversationDatasetById(datasetId);
    }
}
