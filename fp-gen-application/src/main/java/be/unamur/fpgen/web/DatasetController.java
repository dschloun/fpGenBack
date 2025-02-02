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

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * This class is the rest controller for the dataset. It implements the DatasetApi interface.
 */
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

    /**
     * This method is used to add a list of generations to a dataset.
     * @param datasetId the dataset id
     * @param List<UUID> the list of generations id
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> addGenerationListToDataset(UUID datasetId, @Valid List<UUID> UUID) {
        authorVerification.verifyAuthor(datasetId);
        datasetService.addGenerationListToDataset(datasetId, UUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * This method is used to remove a list of generations from a dataset.
     * @param datasetId the dataset id
     * @param List<UUID> the list of generations id
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> removeGenerationFromDataset(UUID datasetId, @Valid List<UUID> UUID) {
        authorVerification.verifyAuthor(datasetId);
        datasetService.removeGenerationListFromDataset(datasetId, UUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * create a dataset
     * @param datasetType
     * @param datasetCreation
     * @return the created dataset
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Dataset> createDataset(@NotNull @Valid DatasetType datasetType, @Valid DatasetCreation datasetCreation) {
        Dataset dataset = DatasetDomainToWebMapper.map(datasetService.createDataset(datasetCreation, DatasetTypeWebToDomainMapper.map(datasetType)));
        return new ResponseEntity<>(dataset, HttpStatus.CREATED);
    }

    /**
     * delete a dataset by its id
     * @param datasetId
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Void> deleteDataset(UUID datasetId) {
        authorVerification.verifyAuthor(datasetId);
        datasetService.deleteDatasetById(datasetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * get a dataset by its id
     * @param datasetId
     * @return the dataset
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Dataset> getDatasetById(UUID datasetId) {
        Dataset dataset = DatasetDomainToWebMapper.map(datasetService.findById(datasetId));

        return new ResponseEntity<>(dataset, HttpStatus.OK);
    }

    /**
     * search datasets by pagination
     * @param pagedDatasetQuery
     * @return the paginated datasets
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<DatasetsPage> searchDatasetsPaginate(@Valid PagedDatasetQuery pagedDatasetQuery) {
        DatasetsPage datasetsPage = DatasetPaginationDomainToWebMapper.map(datasetService.searchDatasetPaginate(DatasetPaginationWebToDomainMapper.map(pagedDatasetQuery)), DatasetType.INSTANT_MESSAGE);
        return new ResponseEntity<>(datasetsPage, HttpStatus.OK);
    }

    /**
     * create a new dataset version
     * @param datasetId
     * @param authorId
     * @return the new dataset version
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Dataset> createNewDatasetVersion(UUID datasetId, @Valid UUID authorId) {
        authorVerification.verifyAuthor(datasetId);
        final be.unamur.fpgen.dataset.Dataset dataset = datasetService.createNewVersion(datasetId, authorId);
        return new ResponseEntity<>(DatasetDomainToWebMapper.map(dataset), HttpStatus.CREATED);
    }

    /**
     * get all dataset versions for a given dataset id
     * @param datasetId
     * @return the list of dataset versions
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<Dataset>> getDatasetAllVersions(UUID datasetId) {
        return new ResponseEntity<>(
                MapperUtil.mapList(datasetService.getAllDatasetVersions(datasetId),
                        DatasetDomainToWebMapper::map),
                HttpStatus.OK);
    }

    /**
     * validate a dataset
     * @param datasetId
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> validateDataset(UUID datasetId) {
        authorVerification.verifyAuthor(datasetId);
        datasetService.validateDataset(datasetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * download a dataset
     * @param datasetId
     * @return the dataset content (csv file)
     */
    @RolesAllowed({"user"})
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

    /**
     * check the bias of a dataset
     * @param datasetId
     * @return the list of real/fake topic bias
     */
    @Override
    public ResponseEntity<List<RealFakeTopicBias>> checkDatasetBias(UUID datasetId) {
        return new ResponseEntity<>(MapperUtil.mapList(datasetService.checkDatasetBias(datasetId), RealFakeTopicBiasDomainToWebMapper::map), HttpStatus.OK);
    }
}
