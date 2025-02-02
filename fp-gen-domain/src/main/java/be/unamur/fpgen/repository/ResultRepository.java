package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.result.Result;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Result
 */
public interface ResultRepository {

    /**
     * Save a result
     * @param dataset the dataset
     * @param author  the author
     * @param result  the result
     * @return the saved result
     */
    Result saveResult(Dataset dataset, Author author, Result result);

    /**
     * Find a result by its id
     * @param resultId the id of the result
     * @return the result
     */
    Optional<Result> findResultById(UUID resultId);

    /**
     * update a result
     * @param existingResult the existing result to update
     * @param result the new result
     */
    Result updateResult(Result existingResult, Result result);

    /**
     * Delete a result
     * @param resultId the id of the result
     */
    void deleteResult(UUID resultId);

    List<Result> findAllResultByDatasetId(UUID datasetId);
}
