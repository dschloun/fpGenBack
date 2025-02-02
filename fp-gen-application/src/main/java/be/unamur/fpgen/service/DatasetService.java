package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.dataset.RealFakeTopicBias;
import be.unamur.fpgen.dataset.pagination.DatasetsPage;
import be.unamur.fpgen.dataset.pagination.PagedDatasetsQuery;
import be.unamur.fpgen.exception.*;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.mapper.webToDomain.DatasetWebToDomainMapper;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.messaging.event.StatisticComputationEvent;
import be.unamur.fpgen.repository.DatasetRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.DatasetCreation;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service to manage datasets
 */
@Service
public class DatasetService implements FindByIdService{
    private final AuthorService authorService;
    private final DatasetRepository datasetRepository;
    private final GenerationService generationService;
    private final ApplicationEventPublisher eventPublisher;

    public DatasetService(AuthorService authorService, DatasetRepository datasetRepository, GenerationService generationService, ApplicationEventPublisher eventPublisher) {
        this.authorService = authorService;
        this.datasetRepository = datasetRepository;
        this.generationService = generationService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Create a dataset
     * @param datasetCreation dataset creation
     * @param type dataset type
     * @return created dataset
     */
    @Transactional
    public Dataset createDataset(DatasetCreation datasetCreation, DatasetTypeEnum type) {
        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());
        return datasetRepository.saveDataset(
                Dataset.newBuilder()
                        .withType(type)
                        .withAuthor(author)
                        .withDatasetFunction(DatasetWebToDomainMapper.mapFunction(datasetCreation.getDatasetFunction()))
                        .withComment(datasetCreation.getComment())
                        .withDescription(datasetCreation.getDescription())
                        .withVersion(0)
                        .withName(datasetCreation.getName())
                        .withLastVersion(true)
                        .build());
    }

    /**
     * Find a dataset by id
     * @param datasetId dataset id
     * @return dataset
     */
    @Transactional
    public Dataset findById(UUID datasetId) {
        return datasetRepository.findDatasetById(datasetId)
                .orElseThrow(() -> DatasetNotFoundException.withId(datasetId));
    }

    /**
     * delete a dataset by id
     * @param datasetId
     */
    @Transactional
    public void deleteDatasetById(UUID datasetId) {
        // 1. get dataset
        final Dataset dataset = findById(datasetId);

        // 2. check if dataset is already validated
        checkDatasetValidationState(dataset, false);

        // 3. if dataset part of a project and is original version throw exception
        if (Objects.isNull(dataset.getOriginalDatasetId()) && datasetRepository.isProjectDataset(dataset.getId())) {
            throw DeleteDatasetException.withId(dataset.getId());
        }

        // 4. find most recent dataset before this one and set it as last version
        final Dataset previousDataset = datasetRepository.findDatasetByOriginalDatasetAndVersion(dataset.getOriginalDatasetId(), dataset.getVersion() - 1)
                .orElseThrow(() -> DatasetNotFoundException.withId(dataset.getOriginalDatasetId()));
        previousDataset.isLastVersionAgain();
        datasetRepository.updateDataset(previousDataset);

        // 3. delete
        datasetRepository.deleteDatasetById(datasetId);
    }

    /**
     * Add generation list to dataset
     * @param datasetId dataset id
     * @param generationIdsList generation id list
     */
    @Transactional
    public void addGenerationListToDataset(UUID datasetId, List<UUID> generationIdsList) {
        // 1. get dataset
        final Dataset dataset = findById(datasetId);

        // 2. check if dataset is already validated
        checkDatasetValidationState(dataset, false);

        // 3. get instant message generations
        final Set<Generation> generationList = getGenerationList(generationIdsList);

        // 4. add instant message generations to dataset
        dataset.getItemList().addAll(generationList);
        datasetRepository.addItemListToDataset(dataset, generationList);

        // 5. send event to compute statistic
        eventPublisher.publishEvent(new StatisticComputationEvent(this, datasetId, DatasetTypeEnum.INSTANT_MESSAGE));
    }

    /**
     * Remove generation list from dataset
     * @param datasetId dataset id
     * @param generationIdsList generation id list
     */
    @Transactional
    public void removeGenerationListFromDataset(UUID datasetId, List<UUID> generationIdsList) {
        // 1. get dataset
        final Dataset dataset = findById(datasetId);

        // 2. check if dataset is already validated
        checkDatasetValidationState(dataset, false);

        // 3. get instant message generations
        final Set<Generation> instantMessageGenerationList = getGenerationList(generationIdsList);

        // 4. remove instant message generations from dataset
        dataset.getItemList().removeAll(getGenerationList(generationIdsList));
        datasetRepository.removeItemListFromDataset(dataset, instantMessageGenerationList);

        // 5. send event to compute statistic
        eventPublisher.publishEvent(new StatisticComputationEvent(this, datasetId, DatasetTypeEnum.INSTANT_MESSAGE));
    }

    /**
     * Search dataset and paginate
     * @param query query
     * @return paginated dataset
     */
    @Transactional
    public DatasetsPage searchDatasetPaginate(final PagedDatasetsQuery query) {
        //1. get pageable
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        //2. search datasets
        return datasetRepository.findPagination(
                query.getDatasetQuery().getType(),
                query.getDatasetQuery().getVersion(),
                query.getDatasetQuery().getName(),
                query.getDatasetQuery().getDescription(),
                query.getDatasetQuery().getComment(),
                query.getDatasetQuery().getAuthorTrigram(),
                DateUtil.ifNullReturnOldDate(query.getDatasetQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getDatasetQuery().getEndDate()),
                pageable);
    }

    /**
     * Add ongoing generation to dataset
     * @param datasetId dataset id
     * @param generation ongoing generation
     */
    @Transactional
    public void addOngoingGenerationToDataset(UUID datasetId, OngoingGeneration generation) {
        final Dataset dataset = findById(datasetId);
        // check if dataset is already validated
        checkDatasetValidationState(dataset, false);
        datasetRepository.addOngoingGenerationToDataset(dataset, generation);
    }

    /**
     * Remove ongoing generation from dataset
     * @param datasetId dataset id
     */
    @Transactional
    public void removeOngoingGenerationFromDataset(UUID datasetId) {
        final Dataset dataset = findById(datasetId);
        // check if dataset is already validated
        checkDatasetValidationState(dataset, false);
        datasetRepository.removeOngoingGenerationFromDataset(dataset);
    }

    /**
     * Validate dataset
     * @param datasetId dataset id
     */
    @Transactional
    public void validateDataset(UUID datasetId) {
        final Dataset dataset = findById(datasetId);
        checkIfDatasetIsEmpty(dataset);
        dataset.validateDataset();
        datasetRepository.updateDataset(dataset);
    }

    /**
     * Create new version of dataset
     * @param oldDatasetId old dataset id
     * @param authorId author id
     * @return new version of dataset
     */
    @Transactional
    public Dataset createNewVersion(UUID oldDatasetId, UUID authorId) {
        // 0. get author
        Author author = null;
        if (Objects.nonNull(authorId)) {
            author = authorService.getAuthorById(authorId);
        }

        // 1. get old dataset and original dataset if exist
        final Dataset oldDataset = findById(oldDatasetId);
        Dataset originalDataset = null;
        if (Objects.nonNull(oldDataset.getOriginalDatasetId())) {
            originalDataset = findById(oldDataset.getOriginalDatasetId());
        }

        // 2. check if old dataset is already validated and is last version
        checkDatasetValidationState(oldDataset, true);
        checkIfDatasetIsLastVersion(oldDataset, true);
        checkIfDatasetIsEmpty(oldDataset);

        // 3. create new version
        final Dataset newVersion = Dataset.newBuilder()
                .withType(oldDataset.getType())
                .withName(Objects.nonNull(originalDataset) ? getNewName(originalDataset, getNewVersion(oldDataset)) : getNewName(oldDataset, getNewVersion(oldDataset)))
                .withDatasetFunction(oldDataset.getDatasetFunction())
                .withAuthor(Objects.nonNull(author) ? author : oldDataset.getAuthor())
                .withVersion(getNewVersion(oldDataset))
                .withLastVersion(true)
                .withOriginalDatasetId(Objects.nonNull(oldDataset.getOriginalDatasetId()) ? oldDataset.getOriginalDatasetId() : oldDataset.getId()) // case if first new version (in the original dataset there is no originalDatasetId
                .withStatistic(oldDataset.getStatistic())
                .build();

        // 4. update old version
        oldDataset.isNotLastVersionAnymore();
        datasetRepository.updateDataset(oldDataset);

        // 5. save new version
        return datasetRepository.saveNewVersion(oldDataset, newVersion);
    }

    /**
     * Get all dataset versions
     * @param datasetId dataset id
     * @return all dataset versions
     */
    @Transactional
    public List<Dataset> getAllDatasetVersions(UUID datasetId) {
        // 1. get the reference dataset
        final Dataset dataset = findById(datasetId);

        // 2. return all dataset version older and newer
        return datasetRepository.findAllDatasetVersions(Objects.nonNull(dataset.getOriginalDatasetId()) ? dataset.getOriginalDatasetId() : datasetId);
    }

    /**
     * Check dataset bias
     * @param datasetId dataset id
     * @return real fake topic bias
     */
    @Transactional
    public List<RealFakeTopicBias> checkDatasetBias(UUID datasetId) {
        // 1. get dataset
        final Dataset dataset = findById(datasetId);

        // 2. get real fake topic bias
        final List<RealFakeTopicBias> realFakeTopicBiasList = new ArrayList<>();

        final Map<String, Integer> real = countReal(dataset);
        final Map<String, Integer> fake = countFake(dataset);
        final Map<MessageTopicEnum, Triple<Integer, Integer, Integer>> difference = computeDifference(real, fake);

        difference.forEach((k, v) -> realFakeTopicBiasList.add(RealFakeTopicBias.newBuilder()
                .withTopic(k)
                .withRealNumber(v.getLeft())
                .withFakeNumber(v.getMiddle())
                .withBias(v.getRight())
                .build()));

        return realFakeTopicBiasList.stream()
                .sorted(Comparator.comparing(i -> i.getTopic().name()))
                .toList();
    }

    /**
     * Get generation list for a generation id list
     * @param generationIdsList
     * @return generation list
     */
    private Set<Generation> getGenerationList(List<UUID> generationIdsList) {
        // 1. get instant message generations
        final Set<Generation> generationList = new HashSet<>();
        generationIdsList.forEach(i -> {
            try {
                final Generation generation = generationService.findById(i);
                generationList.add(generation);
            } catch (GenerationNotFoundException e) {
                System.out.println("generation does not exist");
            }
        });
        // 2. check if list is not empty
        if (generationList.isEmpty()) {
            throw new IllegalArgumentException("No instant message generation found");
        }
        return generationList;
    }

    /**
     * Check if dataset is validated
     * @param dataset dataset
     * @param requiredStatus required status
     * throws DatasetValidatedException if dataset is not validated
     */
    private void checkDatasetValidationState(Dataset dataset, boolean requiredStatus) {
        if (dataset.isValidated() != requiredStatus){
            throw DatasetValidatedException.withId(dataset.getId());
        }
    }

    /**
     * Check if dataset is last version
     * @param dataset dataset
     * @param requiredStatus required status
     * throws DatasetLastVersionException if dataset is not last version
     */
    private void checkIfDatasetIsLastVersion(Dataset dataset, boolean requiredStatus) {
        if (dataset.isLastVersion() != requiredStatus){
            throw DatasetLastVersionException.withId(dataset.getId());
        }
    }

    /**
     * Check if dataset is empty
     * @param dataset dataset
     * throws EmptyDatasetException if dataset is empty
     */
    private void checkIfDatasetIsEmpty(Dataset dataset) {
        if (dataset.getItemList().isEmpty()){
            throw EmptyDatasetException.withId(dataset.getId());
        }
    }

    /**
     * Get new version
     * @param oldDataset old dataset
     * @return new version number
     */
    private int getNewVersion(Dataset oldDataset) {
        return oldDataset.getVersion() + 1;
    }

    /**
     * Get new name
     * @param oldDataset old dataset
     * @param version version
     * @return new name
     */
    private String getNewName(Dataset oldDataset, int version) {
        return String.format("%s%s%s",oldDataset.getName(), " | V-", version);
    }

    /**
     * Count the number of fake messages per topic
     * @param dataset
     * @return a map with the topic as key and the number of fake messages as value
     */
    private Map<String, Integer> countFake(final Dataset dataset) {
        final List<Generation> socialEngineeringGenerationList = dataset.getItemList()
                .stream()
                .filter(i -> MessageTypeEnum.SOCIAL_ENGINEERING.equals(((Generation) i).getType()) || MessageTypeEnum.HARASSMENT.equals(((Generation)i).getType()))
                .map(i -> (Generation) i)
                .toList();

        return countType(socialEngineeringGenerationList);
    }

    /**
     * Count the number of real messages per topic
     * @param dataset
     * @return a map with the topic as key and the number of real messages as value
     */
    private Map<String, Integer> countReal(final Dataset dataset) {
        final List<Generation> socialEngineeringGenerationList = dataset.getItemList()
                .stream()
                .filter(i -> MessageTypeEnum.GENUINE.equals(((Generation) i).getType()))
                .map(i -> (Generation) i)
                .toList();

        return countType(socialEngineeringGenerationList);
    }

    /**
     * Count the number of messages per topic
     * @param generationList
     * @return a map with the topic as key and the number of messages as value
     */
    private Map<String, Integer> countType(final List<Generation> generationList){
        return generationList.stream()
                .collect(Collectors.groupingBy(i -> i.getTopic().name(),
                        Collectors.summingInt(Generation::getQuantity)));
    }

    /**
     * Compute the difference between the number of real and fake messages per topic
     * @param real
     * @param fake
     * @return a map with the topic as key and the number of real, fake and the difference between the two as value
     */
    private Map<MessageTopicEnum, Triple<Integer, Integer, Integer>> computeDifference(final Map<String, Integer> real, final Map<String, Integer> fake) {
        final Set<String> allKeys = new HashSet<>();
        allKeys.addAll(real.keySet());
        allKeys.addAll(fake.keySet());

        final Map<MessageTopicEnum, Triple<Integer, Integer, Integer>> difference = new HashMap<>();
        for (String key : allKeys) {
            int realValue = real.getOrDefault(key, 0);
            int fakeValue = fake.getOrDefault(key, 0);
            difference.put(MessageTopicEnum.valueOf(key), Triple.of(realValue, fakeValue, realValue - fakeValue));
        }
        return difference;
    }
}
