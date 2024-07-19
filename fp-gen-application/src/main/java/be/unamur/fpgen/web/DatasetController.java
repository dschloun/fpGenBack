package be.unamur.fpgen.web;

import be.unamur.api.DatasetApi;
import be.unamur.model.Dataset;
import be.unamur.model.DatasetCreation;
import be.unamur.model.DatasetsPage;
import be.unamur.model.PagedDatasetQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DatasetController implements DatasetApi {
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return DatasetApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> addGenerationListToDataset(UUID datasetId, @Valid List<UUID> UUID) {
        return DatasetApi.super.addGenerationListToDataset(datasetId, UUID);
    }

    @Override
    public ResponseEntity<Dataset> createDataset(@Valid DatasetCreation datasetCreation) {
        return DatasetApi.super.createDataset(datasetCreation);
    }

    @Override
    public ResponseEntity<Void> deleteDataset(UUID datasetId) {
        return DatasetApi.super.deleteDataset(datasetId);
    }

    @Override
    public ResponseEntity<Dataset> getDatasetById(UUID datasetId) {
        return DatasetApi.super.getDatasetById(datasetId);
    }

    @Override
    public ResponseEntity<Void> removeGenerationFromDataset(UUID datasetId, @Valid List<UUID> UUID) {
        return DatasetApi.super.removeGenerationFromDataset(datasetId, UUID);
    }

    @Override
    public ResponseEntity<DatasetsPage> searchDatasetsPaginate(@Valid PagedDatasetQuery pagedDatasetQuery) {
        return DatasetApi.super.searchDatasetsPaginate(pagedDatasetQuery);
    }
}
