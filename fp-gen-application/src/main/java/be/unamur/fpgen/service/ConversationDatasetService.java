package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.exception.GenerationNotFoundException;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.mapper.webToDomain.DatasetWebToDomainMapper;
import be.unamur.fpgen.repository.ConversationDatasetRepository;
import be.unamur.model.DatasetCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ConversationDatasetService {
    private final AuthorService authorService;
    private final ConversationDatasetRepository conversationDatasetRepository;
    private final ConversationGenerationService conversationGenerationService;

    public ConversationDatasetService(AuthorService authorService, ConversationDatasetRepository conversationDatasetRepository, ConversationGenerationService conversationGenerationService) {
        this.authorService = authorService;
        this.conversationDatasetRepository = conversationDatasetRepository;
        this.conversationGenerationService = conversationGenerationService;
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

    @Transactional
    public void addConversationListToDataset(UUID datasetId, List<UUID> conversationGenerationIdsList){
        // 1. get dataset
        final ConversationDataset dataset = getDatasetById(datasetId);

        // 2. get instant message generations
        final Set<ConversationGeneration> conversationGenerationList = new HashSet<>();
        conversationGenerationIdsList.forEach(i -> {
            try {
                final ConversationGeneration instantMessageGeneration = conversationGenerationService.findConversationGenerationById(i);
                conversationGenerationList.add(instantMessageGeneration);
            } catch (GenerationNotFoundException e) {
                System.out.println("generation does not exist");
            }
        });
        // 3. check if list is not empty
        if (conversationGenerationList.isEmpty()) {
            throw new IllegalArgumentException("No instant message generation found");
        }
        // 3. add instant message generations to dataset
        dataset.getConversationGenerationList().addAll(conversationGenerationList);
        conversationDatasetRepository.addConversationListToDataset(dataset, conversationGenerationList);
    }
}
