package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.dataset.pagination.PagedDatasetsQuery;
import be.unamur.fpgen.exception.DatasetNotFoundException;
import be.unamur.fpgen.exception.GenerationNotFoundException;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.mapper.webToDomain.DatasetWebToDomainMapper;
import be.unamur.fpgen.messaging.event.StatisticComputationEvent;
import be.unamur.fpgen.repository.InstantMessageDatasetRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.DatasetCreation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class InstantMessageDatasetService {
    private final AuthorService authorService;
    private final InstantMessageDatasetRepository instantMessageDatasetRepository;
    private final InstantMessageGenerationService instantMessageGenerationService;
    private final ApplicationEventPublisher eventPublisher;

    public InstantMessageDatasetService(AuthorService authorService, InstantMessageDatasetRepository instantMessageDatasetRepository, InstantMessageGenerationService instantMessageGenerationService, ApplicationEventPublisher eventPublisher) {
        this.authorService = authorService;
        this.instantMessageDatasetRepository = instantMessageDatasetRepository;
        this.instantMessageGenerationService = instantMessageGenerationService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public InstantMessageDataset createDataset(DatasetCreation datasetCreation) {
        final Author author = authorService.getAuthorById(datasetCreation.getAuthorId());
        return instantMessageDatasetRepository.saveInstantMessageDataset(
                InstantMessageDataset.newBuilder()
                        .withAuthor(author)
                        .withDatasetFunction(DatasetWebToDomainMapper.mapFunction(datasetCreation.getDatasetFunction()))
                        .withComment(datasetCreation.getComment())
                        .withDescription(datasetCreation.getDescription())
                        .withVersion(0)
                        .withName(datasetCreation.getName())
                        .build());
    }

    @Transactional
    public InstantMessageDataset getDatasetById(UUID datasetId) {
        return instantMessageDatasetRepository.findInstantMessageDatasetById(datasetId)
                .orElseThrow(() -> DatasetNotFoundException.withId(datasetId));
    }

    @Transactional
    public void deleteDatasetById(UUID datasetId) {
        instantMessageDatasetRepository.deleteInstantMessageDatasetById(datasetId);
    }

    @Transactional
    public void addInstantMessageGenerationListToDataset(UUID datasetId, List<UUID> instantMessageGenerationIdsList) {
        // 1. get dataset
        final InstantMessageDataset dataset = getDatasetById(datasetId);

        // 2. get instant message generations
        final Set<InstantMessageGeneration> instantMessageGenerationList = getInstantMessageGenerationList(instantMessageGenerationIdsList);

        // 3. add instant message generations to dataset
        dataset.getInstantMessageGenerationList().addAll(instantMessageGenerationList);
        instantMessageDatasetRepository.addInstantMessageListToDataset(dataset, instantMessageGenerationList);

        // 4. send event to compute statistic
        eventPublisher.publishEvent(new StatisticComputationEvent(this, datasetId, DatasetTypeEnum.INSTANT_MESSAGE));
    }

    @Transactional
    public void removeInstantMessageListFromDataset(UUID datasetId, List<UUID> instantMessageGenerationIdsList) {
        // 1. get dataset
        final InstantMessageDataset dataset = getDatasetById(datasetId);

        // 2. get instant message generations
        final Set<InstantMessageGeneration> instantMessageGenerationList = getInstantMessageGenerationList(instantMessageGenerationIdsList);

        // 3. remove instant message generations from dataset
        dataset.getInstantMessageGenerationList().removeAll(getInstantMessageGenerationList(instantMessageGenerationIdsList));
        instantMessageDatasetRepository.removeInstantMessageListFromDataset(dataset, instantMessageGenerationList);

        // 4. send event to compute statistic
        eventPublisher.publishEvent(new StatisticComputationEvent(this, datasetId, DatasetTypeEnum.INSTANT_MESSAGE));
    }


    @Transactional
    public DatasetsPage searchDatasetPaginate(final PagedDatasetsQuery query) {
        //0. get author (check if author exists)
        final Author author = query.getDatasetQuery().getAuthorTrigram() != null ? authorService.getAuthorByTrigram(query.getDatasetQuery().getAuthorTrigram()) : null;

        //1. get pageable
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        //2. search datasets
        return instantMessageDatasetRepository.findPagination(
                query.getDatasetQuery().getVersion(),
                query.getDatasetQuery().getName(),
                query.getDatasetQuery().getDescription(),
                query.getDatasetQuery().getComment(),
                query.getDatasetQuery().getAuthorTrigram(),
                DateUtil.ifNullReturnOldDate(query.getDatasetQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getDatasetQuery().getEndDate()),
                pageable);
    }

    private Set<InstantMessageGeneration> getInstantMessageGenerationList(List<UUID> instantMessageGenerationIdsList) {
        // 1. get instant message generations
        final Set<InstantMessageGeneration> instantMessageGenerationList = new HashSet<>();
        instantMessageGenerationIdsList.forEach(i -> {
            try {
                final InstantMessageGeneration instantMessageGeneration = instantMessageGenerationService.findInstantMessageGenerationById(i);
                instantMessageGenerationList.add(instantMessageGeneration);
            } catch (GenerationNotFoundException e) {
                System.out.println("generation does not exist");
            }
        });
        // 2. check if list is not empty
        if (instantMessageGenerationList.isEmpty()) {
            throw new IllegalArgumentException("No instant message generation found");
        }
        return instantMessageGenerationList;
    }

    @Transactional
    public void addOngoingGenerationToDataset(UUID datasetId, OngoingGeneration generation) {
        final InstantMessageDataset dataset = getDatasetById(datasetId);
        instantMessageDatasetRepository.addOngoingGenerationToDataset(dataset, generation);
    }

    @Transactional
    public void removeOngoingGenerationToDataset(UUID datasetId, OngoingGeneration generation) {
        final InstantMessageDataset dataset = getDatasetById(datasetId);
        instantMessageDatasetRepository.removeOngoingGenerationToDataset(dataset, generation);
    }

    @Transactional
    public void validateDataset(UUID datasetId) {
        final InstantMessageDataset dataset = getDatasetById(datasetId);
        dataset.validateDataset();
        instantMessageDatasetRepository.updateInstantMessageDataset(dataset);
    }
}
