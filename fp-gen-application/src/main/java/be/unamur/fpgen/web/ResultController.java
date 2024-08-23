package be.unamur.fpgen.web;

import be.unamur.api.ResultApi;
import be.unamur.fpgen.service.ResultService;
import be.unamur.model.Result;
import be.unamur.model.ResultCreation;
import be.unamur.model.ResultUpdate;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public class ResultController implements ResultApi {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @Override
    public ResponseEntity<Result> addResultOnDataset(UUID datasetId, @Valid ResultCreation resultCreation) {
        return new ResponseEntity<>()
    }

    @Override
    public ResponseEntity<Void> deleteResultById(UUID resultId) {
        return ResultApi.super.deleteResultById(resultId);
    }

    @Override
    public ResponseEntity<Result> getResultById(UUID resultId) {
        return ResultApi.super.getResultById(resultId);
    }

    @Override
    public ResponseEntity<List<Result>> getResultListDataset(UUID datasetId) {
        return ResultApi.super.getResultListDataset(datasetId);
    }

    @Override
    public ResponseEntity<Result> updateResultOnDataset(UUID resultId, @Valid ResultUpdate resultUpdate) {
        return ResultApi.super.updateResultOnDataset(resultId, resultUpdate);
    }
}
