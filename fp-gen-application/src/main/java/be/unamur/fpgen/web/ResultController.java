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

/**
 * This rest controller class is the implementation of the ResultApi interface.
 * It is used to manage the results of the datasets.
 */
@Controller
public class ResultController implements ResultApi {

    private final ResultService resultService;
    private final AuthorVerification authorVerification;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
        this.authorVerification = AuthorVerification.newBuilder().withFindByIdService(resultService).build();
    }

    /**
     * This method is used to add a result to a dataset.
     * @param datasetId       the id of the dataset
     * @param resultCreation  the result to add
     * @return the result added
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Result> addResultOnDataset(UUID datasetId, @Valid ResultCreation resultCreation) {
        return new ResponseEntity<>(ResultDomainToWebMapper.map(
                resultService.saveResult(datasetId, ResultWebToDomainMapper.map(resultCreation))), HttpStatus.OK);
    }

    /**
     * This method is used to delete a result by its id.
     * @param resultId  the id of the result to delete
     * @return a response entity with no content
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> deleteResultById(UUID resultId) {
        authorVerification.verifyAuthor(resultId);
        resultService.deleteResult(resultId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * This method is used to get a result by its id.
     * @param resultId  the id of the result to get
     * @return the result
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Result> getResultById(UUID resultId) {
        return new ResponseEntity<>(ResultDomainToWebMapper.map(resultService.findById(resultId)), HttpStatus.OK);
    }

    /**
     * This method is used to get the list of results of a dataset.
     * @param datasetId  the id of the dataset
     * @return the list of results
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<Result>> getResultListDataset(UUID datasetId) {
        return new ResponseEntity<>(MapperUtil.mapList(resultService.findAllResultByDatasetId(datasetId), ResultDomainToWebMapper::map), HttpStatus.OK);
    }

    /**
     * This method is used to update a result by its id.
     * @param resultId     the id of the result to update
     * @param resultUpdate the new result
     * @return the updated result
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Result> updateResultOnDataset(UUID resultId, @Valid ResultUpdate resultUpdate) {
        authorVerification.verifyAuthor(resultId);
        return new ResponseEntity<>(ResultDomainToWebMapper.map(
                resultService.updateResult(resultId, ResultWebToDomainMapper.map(resultUpdate))), HttpStatus.OK);
    }
}
