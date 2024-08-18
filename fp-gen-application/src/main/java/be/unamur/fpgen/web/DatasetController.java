package be.unamur.fpgen.web;

import be.unamur.api.DatasetApi;
import be.unamur.fpgen.mapper.domainToWeb.DatasetDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.pagination.DatasetPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.DatasetTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.DatasetPaginationWebToDomainMapper;
import be.unamur.fpgen.service.DatasetService;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Controller
public class DatasetController implements DatasetApi {
    private final DatasetService datasetService;

    public DatasetController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @Override
    public ResponseEntity<Void> addGenerationListToDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType, @Valid List<UUID> UUID) {
        datasetService.addGenerationListToDataset(datasetId, UUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> removeGenerationFromDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType, @Valid List<UUID> UUID) {
        datasetService.removeGenerationListFromDataset(datasetId, UUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Dataset> createDataset(@NotNull @Valid DatasetType datasetType, @Valid DatasetCreation datasetCreation) {
        Dataset dataset = DatasetDomainToWebMapper.map(datasetService.createDataset(datasetCreation, DatasetTypeWebToDomainMapper.map(datasetType)), DatasetType.INSTANT_MESSAGE);
        return new ResponseEntity<>(dataset, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType) {
        datasetService.deleteDatasetById(datasetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Dataset> getDatasetById(UUID datasetId, @NotNull @Valid DatasetType datasetType) {
        Dataset dataset = DatasetDomainToWebMapper.map(datasetService.getDatasetById(datasetId), DatasetType.INSTANT_MESSAGE);

        return new ResponseEntity<>(dataset, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DatasetsPage> searchDatasetsPaginate(@Valid PagedDatasetQuery pagedDatasetQuery) {
        DatasetsPage datasetsPage = DatasetPaginationDomainToWebMapper.map(datasetService.searchDatasetPaginate(DatasetPaginationWebToDomainMapper.map(pagedDatasetQuery)), DatasetType.INSTANT_MESSAGE);
        return new ResponseEntity<>(datasetsPage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Result> addResultOnDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType, @Valid ResultCreation resultCreation) {
        return DatasetApi.super.addResultOnDataset(datasetId, datasetType, resultCreation);
    }

    @Override
    public ResponseEntity<Dataset> createNewDatasetVersion(UUID datasetId, @NotNull @Valid DatasetType datasetType, @Valid UUID authorId) {
        datasetService.createNewVersion(datasetId, authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<Dataset>> getDatasetAllVersions(UUID datasetId, @NotNull @Valid DatasetType datasetType) {
        return new ResponseEntity<>(
                MapperUtil.mapList(datasetService.getAllDatasetVersions(datasetId),
                        d -> DatasetDomainToWebMapper.map(d, DatasetType.INSTANT_MESSAGE)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteResultOnDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType, UUID resultId) {
        return DatasetApi.super.deleteResultOnDataset(datasetId, datasetType, resultId);
    }

    @Override
    public ResponseEntity<List<Result>> getResultListDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType) {
        return DatasetApi.super.getResultListDataset(datasetId, datasetType);
    }

    @Override
    public ResponseEntity<Result> getResultOnDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType, UUID resultId) {
        return DatasetApi.super.getResultOnDataset(datasetId, datasetType, resultId);
    }

    @Override
    public ResponseEntity<Result> updateResultOnDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType, UUID resultId, @Valid ResultUpdate resultUpdate) {
        return DatasetApi.super.updateResultOnDataset(datasetId, datasetType, resultId, resultUpdate);
    }

    @Override
    public ResponseEntity<Void> validateDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType) {
        datasetService.validateDataset(datasetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
