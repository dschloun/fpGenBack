package be.unamur.fpgen.web;

import be.unamur.api.DatasetApi;
import be.unamur.fpgen.mapper.domainToWeb.DatasetDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.RealFakeTopicBiasDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.pagination.DatasetPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.DatasetTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.DatasetPaginationWebToDomainMapper;
import be.unamur.fpgen.message.download.DocumentContent;
import be.unamur.fpgen.service.DatasetService;
import be.unamur.fpgen.service.DownloadService;
import be.unamur.fpgen.service.identification.AuthorVerification;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class DatasetController implements DatasetApi {
    private static final String ATTACHMENT_FILENAME = "attachment; filename=\"%s\"";

    private final DatasetService datasetService;
    private final DownloadService downloadService;
    private final AuthorVerification authorVerification;

    public DatasetController(DatasetService datasetService,
                             DownloadService downloadService) {
        this.datasetService = datasetService;
        this.downloadService = downloadService;
        this.authorVerification = AuthorVerification.newBuilder().withFindByIdService(datasetService).build();
    }

    @Override
    public ResponseEntity<Void> addGenerationListToDataset(UUID datasetId, @Valid List<UUID> UUID) {
        authorVerification.verifyAuthor(datasetId);
        datasetService.addGenerationListToDataset(datasetId, UUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> removeGenerationFromDataset(UUID datasetId, @Valid List<UUID> UUID) {
        datasetService.removeGenerationListFromDataset(datasetId, UUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Dataset> createDataset(@NotNull @Valid DatasetType datasetType, @Valid DatasetCreation datasetCreation) {
        Dataset dataset = DatasetDomainToWebMapper.map(datasetService.createDataset(datasetCreation, DatasetTypeWebToDomainMapper.map(datasetType)));
        return new ResponseEntity<>(dataset, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteDataset(UUID datasetId) {
        datasetService.deleteDatasetById(datasetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Dataset> getDatasetById(UUID datasetId) {
        Dataset dataset = DatasetDomainToWebMapper.map(datasetService.findById(datasetId));

        return new ResponseEntity<>(dataset, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DatasetsPage> searchDatasetsPaginate(@Valid PagedDatasetQuery pagedDatasetQuery) {
        DatasetsPage datasetsPage = DatasetPaginationDomainToWebMapper.map(datasetService.searchDatasetPaginate(DatasetPaginationWebToDomainMapper.map(pagedDatasetQuery)), DatasetType.INSTANT_MESSAGE);
        return new ResponseEntity<>(datasetsPage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Dataset> createNewDatasetVersion(UUID datasetId, @Valid UUID authorId) {
        final be.unamur.fpgen.dataset.Dataset dataset = datasetService.createNewVersion(datasetId, authorId);
        return new ResponseEntity<>(DatasetDomainToWebMapper.map(dataset), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Dataset>> getDatasetAllVersions(UUID datasetId) {
        return new ResponseEntity<>(
                MapperUtil.mapList(datasetService.getAllDatasetVersions(datasetId),
                        DatasetDomainToWebMapper::map),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> validateDataset(UUID datasetId) {
        datasetService.validateDataset(datasetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Resource> downloadDataset(UUID datasetId) {
        final DocumentContent documentContent = downloadService.downloadDataset(datasetId);

        if (Objects.isNull(documentContent)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format(ATTACHMENT_FILENAME, documentContent.getFileName()));
        headers.add(HttpHeaders.CONTENT_TYPE, documentContent.getMimeType());
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(documentContent.getLength()));

        return new ResponseEntity<>(documentContent.getContentStream(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<RealFakeTopicBias>> checkDatasetBias(UUID datasetId) {
        return new ResponseEntity<>(MapperUtil.mapList(datasetService.checkDatasetBias(datasetId), RealFakeTopicBiasDomainToWebMapper::map), HttpStatus.OK);
    }
}
