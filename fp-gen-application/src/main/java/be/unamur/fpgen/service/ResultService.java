package be.unamur.fpgen.service;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.repository.ResultRepository;
import be.unamur.fpgen.result.Result;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ResultService {
    private final ResultRepository resultRepository;
    private final DatasetService datasetService;

    public ResultService(ResultRepository resultRepository, DatasetService datasetService) {
        this.resultRepository = resultRepository;
        this.datasetService = datasetService;
    }

    @Transactional
    public Result saveResult(UUID datasetId, Result result) {
        final Dataset dataset = datasetService.getDatasetById(datasetId);
        return resultRepository.saveResult(dataset, result);
    }
}
