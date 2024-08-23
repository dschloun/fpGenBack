package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.result.Result;

import java.util.Optional;
import java.util.UUID;

public interface ResultRepository {

    Result saveResult(Dataset dataset, Result result);

    Optional<Result> findResultById(UUID resultId);

}
