package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.dataset.pagination.PagedDatasetsQuery;
import be.unamur.fpgen.exception.*;
import be.unamur.fpgen.generation.Generation;
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
import java.util.*;

@Service
public class InstantMessageDatasetService {
    private final AuthorService authorService;
    private final InstantMessageDatasetRepository instantMessageDatasetRepository;
    private final GenerationService generationService;
    private final ApplicationEventPublisher eventPublisher;

    public InstantMessageDatasetService(AuthorService authorService, InstantMessageDatasetRepository instantMessageDatasetRepository, GenerationService generationService, ApplicationEventPublisher eventPublisher) {
        this.authorService = authorService;
        this.instantMessageDatasetRepository = instantMessageDatasetRepository;
        this.generationService = generationService;
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
                        .withLastVersion(true)
                        .build());
    }

    @Transactional
    public InstantMessageDataset getDatasetById(UUID datasetId) {
        return instantMessageDatasetRepository.findInstantMessageDatasetById(datasetId)
                .orElseThrow(() -> DatasetNotFoundException.withId(datasetId));
    }

    @Transactional
    public void deleteDatasetById(UUID datasetId) {
        // 1. get dataset
        final InstantMessageDataset dataset = getDatasetById(datasetId);

        // 2. check if dataset is already validated
        checkDatasetValidationState(dataset, false);

        // 3. delete
        instantMessageDatasetRepository.deleteInstantMessageDatasetById(datasetId);
    }

    @Transactional
    public void addInstantMessageGenerationListToDataset(UUID datasetId, List<UUID> instantMessageGenerationIdsList) {
        // 1. get dataset
        final InstantMessageDataset dataset = getDatasetById(datasetId);

        // 2. check if dataset is already validated
        checkDatasetValidationState(dataset, false);

        // 3. get instant message generations
        final Set<InstantMessageGeneration> instantMessageGenerationList = getInstantMessageGenerationList(instantMessageGenerationIdsList);

        // 4. add instant message generations to dataset
        dataset.getInstantMessageGenerationList().addAll(instantMessageGenerationList);
        instantMessageDatasetRepository.addInstantMessageListToDataset(dataset, instantMessageGenerationList);

        // 5. send event to compute statistic
        eventPublisher.publishEvent(new StatisticComputationEvent(this, datasetId, DatasetTypeEnum.INSTANT_MESSAGE));
    }

    @Transactional
    public void removeInstantMessageListFromDataset(UUID datasetId, List<UUID> instantMessageGenerationIdsList) {
        // 1. get dataset
        final InstantMessageDataset dataset = getDatasetById(datasetId);

        // 2. check if dataset is already validated
        checkDatasetValidationState(dataset, false);

        // 3. get instant message generations
        final Set<Generation> instantMessageGenerationList = getInstantMessageGenerationList(instantMessageGenerationIdsList);

        // 4. remove instant message generations from dataset
        dataset.getInstantMessageGenerationList().removeAll(getInstantMessageGenerationList(instantMessageGenerationIdsList));
        instantMessageDatasetRepository.removeInstantMessageListFromDataset(dataset, instantMessageGenerationList);

        // 5. send event to compute statistic
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

    private Set<Generation> getInstantMessageGenerationList(List<UUID> instantMessageGenerationIdsList) {
        // 1. get instant message generations
        final Set<Generation> instantMessageGenerationList = new HashSet<>();
        instantMessageGenerationIdsList.forEach(i -> {
            try {
                final InstantMessageGeneration instantMessageGeneration = generationService.findGenerationById(i);
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
        // check if dataset is already validated
        checkDatasetValidationState(dataset, false);
        instantMessageDatasetRepository.addOngoingGenerationToDataset(dataset, generation);
    }

    @Transactional
    public void removeOngoingGenerationToDataset(UUID datasetId, OngoingGeneration generation) {
        final InstantMessageDataset dataset = getDatasetById(datasetId);
        // check if dataset is already validated
        checkDatasetValidationState(dataset, false);
        instantMessageDatasetRepository.removeOngoingGenerationToDataset(dataset, generation);
    }

    @Transactional
    public void validateDataset(UUID datasetId) {
        final InstantMessageDataset dataset = getDatasetById(datasetId);
        checkIfDatasetIsEmpty(dataset);
        dataset.validateDataset();
        instantMessageDatasetRepository.updateInstantMessageDataset(dataset);
    }

    @Transactional
    public InstantMessageDataset createNewVersion(UUID oldDatasetId, UUID authorId) {
        // 0. get author
        Author author = null;
        if (Objects.nonNull(authorId)) {
            author = authorService.getAuthorById(authorId);
        }

        // 1. get old dataset and original dataset if exist
        final InstantMessageDataset oldDataset = getDatasetById(oldDatasetId);
        InstantMessageDataset originalDataset = null;
        if (Objects.nonNull(oldDataset.getOriginalDatasetId())) {
            originalDataset = getDatasetById(oldDataset.getOriginalDatasetId());
        }

        // 2. check if old dataset is already validated and is last version
        checkDatasetValidationState(oldDataset, true);
        checkIfDatasetIsLastVersion(oldDataset, true);
        checkIfDatasetIsEmpty(oldDataset);

        // 3. create new version
        final InstantMessageDataset newVersion = InstantMessageDataset.newBuilder()
                .withName(Objects.nonNull(originalDataset) ? getNewName(originalDataset, getNewVersion(oldDataset)) : getNewName(oldDataset, getNewVersion(oldDataset)))
                .withDatasetFunction(oldDataset.getDatasetFunction())
                .withAuthor(Objects.nonNull(author) ? author : oldDataset.getAuthor())
                .withVersion(getNewVersion(oldDataset))
                .withLastVersion(true)
                .withOriginalDatasetId(Objects.nonNull(oldDataset.getOriginalDatasetId()) ? oldDataset.getOriginalDatasetId() : oldDataset.getId()) // case if first new version (in the original dataset there is no originalDatasetId
                .build();

        // 4. update old version
        oldDataset.isNotLastVersionAnymore();
        instantMessageDatasetRepository.updateInstantMessageDataset(oldDataset);

        // 5. save new version
        return instantMessageDatasetRepository.saveNewVersion(oldDataset, newVersion);
    }

    private void checkDatasetValidationState(InstantMessageDataset dataset, boolean requiredStatus) {
        if (dataset.isValidated() != requiredStatus){
            throw DatasetValidatedException.withId(dataset.getId());
        }
    }

    private void checkIfDatasetIsLastVersion(InstantMessageDataset dataset, boolean requiredStatus) {
        if (dataset.isLastVersion() != requiredStatus){
            throw DatasetLastVersionException.withId(dataset.getId());
        }
    }

    private void checkIfDatasetIsEmpty(InstantMessageDataset dataset) {
        if (dataset.getInstantMessageGenerationList().isEmpty()){
            throw EmptyDatasetException.withId(dataset.getId());
        }
    }

    private int getNewVersion(InstantMessageDataset oldDataset) {
        return oldDataset.getVersion() + 1;
    }

    private String getNewName(InstantMessageDataset oldDataset, int version) {
        return String.format("%s%s%s",oldDataset.getName(), " | V-", version);
    }

    @Transactional
    public List<InstantMessageDataset> getAllDatasetVersions(UUID datasetId) {
        // 1. get the reference dataset
        final InstantMessageDataset dataset = getDatasetById(datasetId);

        // 2. return all dataset version older and newer
        return instantMessageDatasetRepository.findAllDatasetVersions(Objects.nonNull(dataset.getOriginalDatasetId()) ? dataset.getOriginalDatasetId() : datasetId);
    }
}
