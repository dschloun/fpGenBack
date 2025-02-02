package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.exception.ResultNotFoundException;
import be.unamur.fpgen.repository.ResultRepository;
import be.unamur.fpgen.result.Result;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Service class for Result
 */
@Service
public class ResultService implements FindByIdService{
    private final ResultRepository resultRepository;
    private final DatasetService datasetService;
    private final AuthorService authorService;

    public ResultService(ResultRepository resultRepository, DatasetService datasetService, AuthorService authorService) {
        this.resultRepository = resultRepository;
        this.datasetService = datasetService;
        this.authorService = authorService;
    }

    /**
     * Save a result
     * @param datasetId the dataset id
     * @param result the result to save
     * @return the saved result
     */
    @Transactional
    public Result saveResult(UUID datasetId, Result result) {
        final Dataset dataset = datasetService.findById(datasetId);
        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());
        return resultRepository.saveResult(dataset, author, result);
    }

    /**
     * Find a result by its id
     * @param resultId the result id
     * @return the result
     */
    @Transactional
    public Result findById(UUID resultId) {
        return resultRepository.findResultById(resultId).orElseThrow(() -> ResultNotFoundException.withId(resultId));
    }

    /**
     * Update a result
     * @param resultId the result id
     * @param result the result to update
     * @return the updated result
     */
    @Transactional
    public Result updateResult(UUID resultId, Result result) {
        final Result existingResult = this.findById(resultId);
        return resultRepository.updateResult(existingResult, result);
    }

    /**
     * Delete a result
     * @param resultId the result id
     */
    @Transactional
    public void deleteResult(UUID resultId) {
        resultRepository.deleteResult(resultId);
    }

    /**
     * Find all results by dataset id
     * @param datasetId the dataset id
     * @return the list of results
     */
    @Transactional
    public List<Result> findAllResultByDatasetId(UUID datasetId) {
        return resultRepository.findAllResultByDatasetId(datasetId);
    }

}
