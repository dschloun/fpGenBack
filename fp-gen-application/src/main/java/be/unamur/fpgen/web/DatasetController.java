package be.unamur.fpgen.web;

import be.unamur.api.DatasetApi;
import be.unamur.fpgen.mapper.domainToWeb.DatasetDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.pagination.DatasetPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.DatasetPaginationWebToDomainMapper;
import be.unamur.fpgen.service.ConversationDatasetService;
import be.unamur.fpgen.service.InstantMessageDatasetService;
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
    private final InstantMessageDatasetService instantMessageDatasetService;
    private final ConversationDatasetService conversationDatasetService;

    public DatasetController(InstantMessageDatasetService instantMessageDatasetService, ConversationDatasetService conversationDatasetService) {
        this.instantMessageDatasetService = instantMessageDatasetService;
        this.conversationDatasetService = conversationDatasetService;
    }

    @Override
    public ResponseEntity<Void> addGenerationListToDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType, @Valid List<UUID> UUID) {
        if (DatasetType.INSTANT_MESSAGE.equals(datasetType)){
            instantMessageDatasetService.addInstantMessageListToDataset(datasetId, UUID);
        } else if(DatasetType.CONVERSATION.equals(datasetType)){
            conversationDatasetService.addConversationListToDataset(datasetId, UUID);
        } else {
            throw new IllegalArgumentException("Unsupported dataset type");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> removeGenerationFromDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType, @Valid List<UUID> UUID) {
        if (DatasetType.INSTANT_MESSAGE.equals(datasetType)){
            instantMessageDatasetService.removeInstantMessageListFromDataset(datasetId, UUID);
        } else if(DatasetType.CONVERSATION.equals(datasetType)){
            conversationDatasetService.removeConversationListFromDataset(datasetId, UUID);
        } else {
            throw new IllegalArgumentException("Unsupported dataset type");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Dataset> createDataset(@NotNull @Valid DatasetType datasetType, @Valid DatasetCreation datasetCreation) {
        Dataset dataset;
        if (DatasetType.INSTANT_MESSAGE.equals(datasetType)){
            dataset = DatasetDomainToWebMapper.map(instantMessageDatasetService.createDataset(datasetCreation), DatasetType.INSTANT_MESSAGE);
        } else if(DatasetType.CONVERSATION.equals(datasetType)){
            dataset = DatasetDomainToWebMapper.map(conversationDatasetService.createDataset(datasetCreation), DatasetType.CONVERSATION);
        } else {
            throw new IllegalArgumentException("Unsupported dataset type");
        }
        return new ResponseEntity<>(dataset, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteDataset(UUID datasetId, @NotNull @Valid DatasetType datasetType) {
        if (DatasetType.INSTANT_MESSAGE.equals(datasetType)){
            instantMessageDatasetService.deleteDatasetById(datasetId);
        } else if(DatasetType.CONVERSATION.equals(datasetType)){
            conversationDatasetService.deleteDatasetById(datasetId);
        } else {
            throw new IllegalArgumentException("Unsupported dataset type");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Dataset> getDatasetById(UUID datasetId, @NotNull @Valid DatasetType datasetType) {
        Dataset dataset;
        if (DatasetType.INSTANT_MESSAGE.equals(datasetType)){
            dataset = DatasetDomainToWebMapper.map(instantMessageDatasetService.getDatasetById(datasetId), DatasetType.INSTANT_MESSAGE);
        } else if(DatasetType.CONVERSATION.equals(datasetType)){
            dataset = DatasetDomainToWebMapper.map(conversationDatasetService.getDatasetById(datasetId), DatasetType.CONVERSATION);
        } else {
            throw new IllegalArgumentException("Unsupported dataset type");
        }
        return new ResponseEntity<>(dataset, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DatasetsPage> searchDatasetsPaginate(@NotNull @Valid DatasetType datasetType, @Valid PagedDatasetQuery pagedDatasetQuery) {
        DatasetsPage datasetsPage;
        if (DatasetType.INSTANT_MESSAGE.equals(datasetType)) {
            datasetsPage = DatasetPaginationDomainToWebMapper.map(instantMessageDatasetService.searchDatasetPaginate(DatasetPaginationWebToDomainMapper.map(pagedDatasetQuery)), DatasetType.INSTANT_MESSAGE);
        } else if(DatasetType.CONVERSATION.equals(datasetType)){
            datasetsPage = DatasetPaginationDomainToWebMapper.map(conversationDatasetService.searchDatasetPaginate(DatasetPaginationWebToDomainMapper.map(pagedDatasetQuery)), DatasetType.CONVERSATION);
        }
        else {
            throw new IllegalArgumentException("Unsupported dataset type");
        }
        return new ResponseEntity<>(datasetsPage, HttpStatus.OK);
    }
}
