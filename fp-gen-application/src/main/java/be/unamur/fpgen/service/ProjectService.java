package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.dataset.DatasetFunctionEnum;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.exception.DatasetNotFoundException;
import be.unamur.fpgen.exception.ProjectNotFoundException;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.mapper.webToDomain.ProjectWebToDomainMapper;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.project.Project;
import be.unamur.fpgen.project.TrainingTestDifference;
import be.unamur.fpgen.project.TypeTopicDifference;
import be.unamur.fpgen.project.pagination.PagedProjectsQuery;
import be.unamur.fpgen.project.pagination.ProjectsPage;
import be.unamur.fpgen.repository.ProjectRepository;
import be.unamur.model.ProjectCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for project.
 */
@Service
public class ProjectService implements FindByIdService{
    private final AuthorService authorService;
    private final ProjectRepository projectRepository;

    public ProjectService(AuthorService authorService, ProjectRepository projectRepository) {
        this.authorService = authorService;
        this.projectRepository = projectRepository;
    }

    /**
     * Create a project.
     * @param projectCreation the project creation object
     * @return the created project
     */
    @Transactional
    public Project createProject(ProjectCreation projectCreation){
        // 0. get author
        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());

        // 1. generate 3 datasets for the project
        final Project project = ProjectWebToDomainMapper.map(projectCreation, author);
        project.generateInitialDatasets(getDatasetType(projectCreation));

        return projectRepository.save(project, author);
    }

    /**
     * Find a project by its id.
     * @param projectId the project id
     * @return the project throw an exception if not found
     */
    @Transactional
    public Project findById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> ProjectNotFoundException.withId(projectId));
    }

    /**
     * Search projects with pagination.
     * @param query the query
     * @return the projects page
     */
    @Transactional
    public ProjectsPage searchProjectPaginate(final PagedProjectsQuery query){
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        return projectRepository.findPagination(
                query.getProjectQuery().getType(),
                query.getProjectQuery().getName(),
                query.getProjectQuery().getDescription(),
                query.getProjectQuery().getOrganization(),
                query.getProjectQuery().getAuthorTrigram(),
                query.getProjectQuery().getStartDate(),
                query.getProjectQuery().getEndDate(),
                pageable);
    }

    /**
     * Delete a project by its id.
     * @param projectId the project id
     */
    @Transactional
    public void deleteById(UUID projectId){
        projectRepository.deleteById(projectId);
    }

    /**
     * Compute the difference between training and test datasets.
     * @param projectId the project id
     * @return the training test difference
     */
    @Transactional
    public TrainingTestDifference computeTrainingTestDifference(UUID projectId){
        // 0. get project
        final Project project = findById(projectId);

        // 1. get training and test datasets
        final Dataset trainingDataset = project.getDatasetList().stream()
                .filter(dataset -> dataset.getDatasetFunction().equals(DatasetFunctionEnum.TRAINING))
                .findFirst()
                .orElseThrow(() -> DatasetNotFoundException.withFunction(DatasetFunctionEnum.TRAINING.name(), projectId));
        final Dataset testDataset = project.getDatasetList().stream()
                .filter(dataset -> dataset.getDatasetFunction().equals(DatasetFunctionEnum.TEST))
                .findFirst()
                .orElseThrow(() -> DatasetNotFoundException.withFunction(DatasetFunctionEnum.TEST.name(), projectId));

        // 2. compute difference
        // 2.1 count fake
        final Integer fakeTraining = countFake(trainingDataset);
        final Integer fakeTest = countFake(testDataset);
        final Integer fakeDifference = fakeTraining - fakeTest;

        // 2.2 count real
        final Integer realTraining = countReal(trainingDataset);
        final Integer realTest = countReal(testDataset);
        final Integer realDifference = realTraining - realTest;

        // 2.3 count social engineering
        final Integer socialEngineeringTraining = countSocialEngineering(trainingDataset);
        final Integer socialEngineeringTest = countSocialEngineering(testDataset);
        final Integer socialEngineeringDifference = socialEngineeringTraining - socialEngineeringTest;

        // 2.4 count harassment
        final Integer harassmentTraining = countHarassment(trainingDataset);
        final Integer harassmentTest = countHarassment(testDataset);
        final Integer harassmentDifference = harassmentTraining - harassmentTest;

        // 3. type topic details
        final Map<String, Integer> trainingTypeTopic = countTypeTopic(trainingDataset);
        final Map<String, Integer> testTypeTopic = countTypeTopic(testDataset);
        final Set<TypeTopicDifference> typeTopicDifferences = computeTypeTopicDifference(trainingTypeTopic, testTypeTopic);

        // 4. return
        return TrainingTestDifference.newBuilder()
                .withFakeDifference(fakeDifference)
                .withRealDifference(realDifference)
                .withSocialEngineeringDifference(socialEngineeringDifference)
                .withHarassmentDifference(harassmentDifference)
                .withTypeTopicDifferences(typeTopicDifferences)
                .build();
    }

    /**
     * Get the dataset type from the project creation.
     * @param projectCreation the project creation
     * @return the dataset type
     */
    private DatasetTypeEnum getDatasetType(final ProjectCreation projectCreation){
        if (projectCreation.getProjectType().name().equals(DatasetTypeEnum.INSTANT_MESSAGE.name())){
            return DatasetTypeEnum.INSTANT_MESSAGE;
        } else {
            return DatasetTypeEnum.CONVERSATION;
        }
    }

    /**
     * Count the number of fake messages in a dataset.
     * @param dataset the dataset
     * @return the number of fake messages
     */
    private Integer countFake(final Dataset dataset){
        return dataset.getItemList()
                .stream()
                .filter(i -> MessageTypeEnum.SOCIAL_ENGINEERING.equals(((Generation)i).getType()) || MessageTypeEnum.HARASSMENT.equals(((Generation)i).getType()))
                .map(i -> ((Generation)i).getQuantity())
                .reduce(0, Integer::sum);
    }

    /**
     * Count the number of real messages in a dataset.
     * @param dataset the dataset
     * @return the number of real messages
     */
    private Integer countReal(final Dataset dataset){
        return dataset.getItemList()
                .stream()
                .filter(i -> MessageTypeEnum.GENUINE.equals(((Generation)i).getType()))
                .map(i -> ((Generation)i).getQuantity())
                .reduce(0, Integer::sum);
    }

    /**
     * Count the number of social engineering messages in a dataset.
     * @param dataset
     * @return the number of social engineering messages
     */
    private Integer countSocialEngineering(final Dataset dataset){
        return dataset.getItemList()
                .stream()
                .filter(i -> MessageTypeEnum.SOCIAL_ENGINEERING.equals(((Generation)i).getType()))
                .map(i -> ((Generation)i).getQuantity())
                .reduce(0, Integer::sum);
    }

    /**
     * Count the number of harassment messages in a dataset.
     * @param dataset the dataset
     * @return the number of harassment messages
     */
    private Integer countHarassment(final Dataset dataset){
        return dataset.getItemList()
                .stream()
                .filter(i -> MessageTypeEnum.HARASSMENT.equals(((Generation)i).getType()))
                .map(i -> ((Generation)i).getQuantity())
                .reduce(0, Integer::sum);
    }

    /**
     * Count the number of messages per type and topic in a dataset.
     * @param dataset the dataset
     * @return the map of type and topic to the number of messages
     */
    private Map<String, Integer> countTypeTopic(final Dataset dataset){
        return dataset.getItemList()
                .stream()
                .collect(Collectors.groupingBy(i -> ((Generation)i).getType().name() + "|" + ((Generation)i).getTopic().name(),
                        Collectors.summingInt(i -> ((Generation)i).getQuantity())));
    }

    /**
     * Compute the difference between training and test datasets for each type and topic.
     * @param training the training dataset
     * @param test the test dataset
     * @return the set of type topic differences
     */
    private Set<TypeTopicDifference> computeTypeTopicDifference(final Map<String, Integer> training, final Map<String, Integer> test){
        // Create a set of all keys from both training and test datasets
        Set<String> allKeys = new HashSet<>(training.keySet());
        allKeys.addAll(test.keySet());

        return allKeys.stream()
                .map(key -> {
                    final Integer trainingValue = training.getOrDefault(key, 0);
                    final Integer testValue = test.getOrDefault(key, 0);
                    return TypeTopicDifference.newBuilder()
                            .withKey(key)
                            .withType(MessageTypeEnum.valueOf(key.split("\\|")[0]))
                            .withTopic(MessageTopicEnum.valueOf(key.split("\\|")[1]))
                            .withDifference(trainingValue - testValue)
                            .build();
                })
                .collect(Collectors.toSet());
    }
}