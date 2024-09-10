package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
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
import be.unamur.fpgen.project.TypeTopicDifference;
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

@Service
public class DatasetService {
    private final AuthorService authorService;
    private final DatasetRepository datasetRepository;
    private final GenerationService generationService;
    private final OngoingGenerationService ongoingGenerationService;
    private final ApplicationEventPublisher eventPublisher;

    public DatasetService(AuthorService authorService, DatasetRepository datasetRepository, GenerationService generationService, OngoingGenerationService ongoingGenerationService, ApplicationEventPublisher eventPublisher) {
        this.authorService = authorService;
        this.datasetRepository = datasetRepository;
        this.generationService = generationService;
        this.ongoingGenerationService = ongoingGenerationService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Dataset createDataset(DatasetCreation datasetCreation, DatasetTypeEnum type) {
        final Author author = authorService.getAuthorById(datasetCreation.getAuthorId());
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

    @Transactional
    public Dataset getDatasetById(UUID datasetId) {
        return datasetRepository.findDatasetById(datasetId)
                .orElseThrow(() -> DatasetNotFoundException.withId(datasetId));
    }

    @Transactional
    public void deleteDatasetById(UUID datasetId) {
        // 1. get dataset
        final Dataset dataset = getDatasetById(datasetId);

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

    @Transactional
    public void addGenerationListToDataset(UUID datasetId, List<UUID> generationIdsList) {
        // 1. get dataset
        final Dataset dataset = getDatasetById(datasetId);

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

    @Transactional
    public void removeGenerationListFromDataset(UUID datasetId, List<UUID> generationIdsList) {
        // 1. get dataset
        final Dataset dataset = getDatasetById(datasetId);

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

    private Set<Generation> getGenerationList(List<UUID> generationIdsList) {
        // 1. get instant message generations
        final Set<Generation> generationList = new HashSet<>();
        generationIdsList.forEach(i -> {
            try {
                final Generation generation = generationService.findGenerationById(i);
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

    @Transactional
    public void addOngoingGenerationToDataset(UUID datasetId, OngoingGeneration generation) {
        final Dataset dataset = getDatasetById(datasetId);
        // check if dataset is already validated
        checkDatasetValidationState(dataset, false);
        datasetRepository.addOngoingGenerationToDataset(dataset, generation);
    }

    @Transactional
    public void removeOngoingGenerationFromDataset(UUID datasetId) {
        final Dataset dataset = getDatasetById(datasetId);
        // check if dataset is already validated
        checkDatasetValidationState(dataset, false);
        datasetRepository.removeOngoingGenerationFromDataset(dataset);
    }

    @Transactional
    public void validateDataset(UUID datasetId) {
        final Dataset dataset = getDatasetById(datasetId);
        checkIfDatasetIsEmpty(dataset);
        dataset.validateDataset();
        datasetRepository.updateDataset(dataset);
    }

    @Transactional
    public Dataset createNewVersion(UUID oldDatasetId, UUID authorId) {
        // 0. get author
        Author author = null;
        if (Objects.nonNull(authorId)) {
            author = authorService.getAuthorById(authorId);
        }

        // 1. get old dataset and original dataset if exist
        final Dataset oldDataset = getDatasetById(oldDatasetId);
        Dataset originalDataset = null;
        if (Objects.nonNull(oldDataset.getOriginalDatasetId())) {
            originalDataset = getDatasetById(oldDataset.getOriginalDatasetId());
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

    private void checkDatasetValidationState(Dataset dataset, boolean requiredStatus) {
        if (dataset.isValidated() != requiredStatus){
            throw DatasetValidatedException.withId(dataset.getId());
        }
    }

    private void checkIfDatasetIsLastVersion(Dataset dataset, boolean requiredStatus) {
        if (dataset.isLastVersion() != requiredStatus){
            throw DatasetLastVersionException.withId(dataset.getId());
        }
    }

    private void checkIfDatasetIsEmpty(Dataset dataset) {
        if (dataset.getItemList().isEmpty()){
            throw EmptyDatasetException.withId(dataset.getId());
        }
    }

    private int getNewVersion(Dataset oldDataset) {
        return oldDataset.getVersion() + 1;
    }

    private String getNewName(Dataset oldDataset, int version) {
        return String.format("%s%s%s",oldDataset.getName(), " | V-", version);
    }

    @Transactional
    public List<Dataset> getAllDatasetVersions(UUID datasetId) {
        // 1. get the reference dataset
        final Dataset dataset = getDatasetById(datasetId);

        // 2. return all dataset version older and newer
        return datasetRepository.findAllDatasetVersions(Objects.nonNull(dataset.getOriginalDatasetId()) ? dataset.getOriginalDatasetId() : datasetId);
    }

    @Transactional
    public List<RealFakeTopicBias> checkDatasetBias(UUID datasetId) {
        // 1. get dataset
        final Dataset dataset = getDatasetById(datasetId);

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

    private Map<String, Integer> countFake(final Dataset dataset) {
        final List<Generation> socialEngineeringGenerationList = dataset.getItemList()
                .stream()
                .filter(i -> MessageTypeEnum.SOCIAL_ENGINEERING.equals(((Generation) i).getType()) || MessageTypeEnum.TROLLING.equals(((Generation)i).getType()))
                .map(i -> (Generation) i)
                .toList();

        return countType(socialEngineeringGenerationList);
    }

    private Map<String, Integer> countReal(final Dataset dataset) {
        final List<Generation> socialEngineeringGenerationList = dataset.getItemList()
                .stream()
                .filter(i -> MessageTypeEnum.GENUINE.equals(((Generation) i).getType()))
                .map(i -> (Generation) i)
                .toList();

        return countType(socialEngineeringGenerationList);
    }

    private Map<String, Integer> countType(final List<Generation> generationList){
        return generationList.stream()
                .collect(Collectors.groupingBy(i -> i.getTopic().name(),
                        Collectors.summingInt(Generation::getQuantity)));
    }

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
