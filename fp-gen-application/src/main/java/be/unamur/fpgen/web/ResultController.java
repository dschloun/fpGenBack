package be.unamur.fpgen.web;

import be.unamur.api.ResultApi;
import be.unamur.fpgen.mapper.domainToWeb.ResultDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.ResultWebToDomainMapper;
import be.unamur.fpgen.service.ResultService;
import be.unamur.fpgen.service.identification.AuthorVerification;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.Result;
import be.unamur.model.ResultCreation;
import be.unamur.model.ResultUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class ResultController implements ResultApi {

    private final ResultService resultService;
    private final AuthorVerification authorVerification;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
        this.authorVerification = AuthorVerification.newBuilder().withFindByIdService(resultService).build();
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Result> addResultOnDataset(UUID datasetId, @Valid ResultCreation resultCreation) {
        return new ResponseEntity<>(ResultDomainToWebMapper.map(
                resultService.saveResult(datasetId, ResultWebToDomainMapper.map(resultCreation))), HttpStatus.OK);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> deleteResultById(UUID resultId) {
        authorVerification.verifyAuthor(resultId);
        resultService.deleteResult(resultId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Result> getResultById(UUID resultId) {
        return new ResponseEntity<>(ResultDomainToWebMapper.map(resultService.findById(resultId)), HttpStatus.OK);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<Result>> getResultListDataset(UUID datasetId) {
        return new ResponseEntity<>(MapperUtil.mapList(resultService.findAllResultByDatasetId(datasetId), ResultDomainToWebMapper::map), HttpStatus.OK);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Result> updateResultOnDataset(UUID resultId, @Valid ResultUpdate resultUpdate) {
        authorVerification.verifyAuthor(resultId);
        return new ResponseEntity<>(ResultDomainToWebMapper.map(
                resultService.updateResult(resultId, ResultWebToDomainMapper.map(resultUpdate))), HttpStatus.OK);
    }
}
