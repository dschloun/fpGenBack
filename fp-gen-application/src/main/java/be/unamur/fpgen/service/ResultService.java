package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.exception.ResultNotFoundException;
import be.unamur.fpgen.repository.ResultRepository;
import be.unamur.fpgen.result.Result;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

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

    @Transactional
    public Result saveResult(UUID datasetId, UUID authorId, Result result) {
        final Dataset dataset = datasetService.findById(datasetId);
        final Author author = authorService.getAuthorById(authorId);
        return resultRepository.saveResult(dataset, author, result);
    }

    @Transactional
    public Result findById(UUID resultId) {
        return resultRepository.findResultById(resultId).orElseThrow(() -> ResultNotFoundException.withId(resultId));
    }

    @Transactional
    public Result updateResult(UUID resultId, Result result) {
        final Result existingResult = this.findById(resultId);
        return resultRepository.updateResult(existingResult, result);
    }

    @Transactional
    public void deleteResult(UUID resultId) {
        resultRepository.DeleteResult(resultId);
    }

    @Transactional
    public List<Result> findAllResultByDatasetId(UUID datasetId) {
        return resultRepository.findAllResultByDatasetId(datasetId);
    }

}
