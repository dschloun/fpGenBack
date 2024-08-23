package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.result.Result;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResultRepository {

    Result saveResult(Dataset dataset, Author author, Result result);

    Optional<Result> findResultById(UUID resultId);

    Result updateResult(Result result);

    void DeleteResult(UUID resultId);

    List<Result> findAllResultByDatasetId(UUID datasetId);
}
