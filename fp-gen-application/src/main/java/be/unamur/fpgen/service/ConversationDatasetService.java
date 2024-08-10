package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.dataset.pagination.PagedDatasetsQuery;
import be.unamur.fpgen.exception.GenerationNotFoundException;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.mapper.webToDomain.DatasetWebToDomainMapper;
import be.unamur.fpgen.repository.ConversationDatasetRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.DatasetCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                        .withDatasetFunction(DatasetWebToDomainMapper.mapFunction(datasetCreation.getDatasetFunction()))
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
    public void addConversationGenerationListToDataset(UUID datasetId, List<UUID> conversationGenerationIdsList){
        // 1. get dataset
        final ConversationDataset dataset = getDatasetById(datasetId);

        // 2. get conversation message generations
        final Set<ConversationGeneration> conversationGenerationList = getConversationGenerationList(conversationGenerationIdsList);

        // 3. add conversation message generations to dataset
        dataset.getConversationGenerationList().addAll(conversationGenerationList);
        conversationDatasetRepository.addConversationListToDataset(dataset, conversationGenerationList);
    }

    @Transactional
    public void removeConversationListFromDataset(UUID datasetId, List<UUID> conversationGenerationIdsList) {
        // 1. get dataset
        final ConversationDataset dataset = getDatasetById(datasetId);

        // 2. get conversation message generations
        final Set<ConversationGeneration> conversationGenerationList = getConversationGenerationList(conversationGenerationIdsList);

        // 3. remove conversation message generations from dataset
        dataset.getConversationGenerationList().removeAll(getConversationGenerationList(conversationGenerationIdsList));
        conversationDatasetRepository.removeConversationListFromDataset(dataset, conversationGenerationList);
    }

    @Transactional
    public DatasetsPage searchDatasetPaginate(final PagedDatasetsQuery query) {
        //0. get author
        final Author author = query.getDatasetQuery().getAuthorTrigram() != null ? authorService.getAuthorByTrigram(query.getDatasetQuery().getAuthorTrigram()) : null;

        //1. get pageable
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        //2. search datasets
        return conversationDatasetRepository.findPagination(
                query.getDatasetQuery().getVersion(),
                query.getDatasetQuery().getName(),
                query.getDatasetQuery().getDescription(),
                query.getDatasetQuery().getComment(),
                query.getDatasetQuery().getAuthorTrigram(),
                DateUtil.ifNullReturnOldDate(query.getDatasetQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getDatasetQuery().getEndDate()),
                pageable);
    }

    private Set<ConversationGeneration> getConversationGenerationList(List<UUID> conversationGenerationIdsList) {
        // 1. get instant message generations
        final Set<ConversationGeneration> conversationGenerationList = new HashSet<>();
        conversationGenerationIdsList.forEach(i -> {
            try {
                final ConversationGeneration conversationGeneration = conversationGenerationService.findConversationGenerationById(i);
                conversationGenerationList.add(conversationGeneration);
            } catch (GenerationNotFoundException e) {
                System.out.println("generation does not exist");
            }
        });
        // 2. check if list is not empty
        if (conversationGenerationList.isEmpty()) {
            throw new IllegalArgumentException("No conversation message generation found");
        }
        return conversationGenerationList;
    }
}
