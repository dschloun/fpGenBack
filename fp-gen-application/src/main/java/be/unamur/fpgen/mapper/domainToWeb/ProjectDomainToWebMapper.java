package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.dataset.DatasetFunctionEnum;
import be.unamur.fpgen.project.ProjectTypeEnum;
import be.unamur.model.DatasetType;
import be.unamur.model.Project;
import be.unamur.model.ProjectType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProjectDomainToWebMapper {

    public static Project map(be.unamur.fpgen.project.Project domain) {
        return new Project()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .modificationDate(domain.getModificationDate())
                .name(domain.getName())
                .description(domain.getDescription())
                .organization(domain.getOrganisation())
                .type(map(domain.getType()))
                .author(AuthorDomainToWebMapper.map(domain.getAuthor()))
                .trainingDataset(mapAndGetLastVersion(domain.getDatasetList(), DatasetFunctionEnum.TRAINING))
                .testDataset(mapAndGetLastVersion(domain.getDatasetList(), DatasetFunctionEnum.TEST))
                .validationDataset(mapAndGetLastVersion(domain.getDatasetList(), DatasetFunctionEnum.VALIDATION));
    }

    public static ProjectType map(final ProjectTypeEnum domain) {
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(ProjectType::valueOf)
                .orElse(null);
    }

    private static DatasetType getType(Dataset dataset) {
        if (dataset instanceof InstantMessageDataset) {
            return DatasetType.INSTANT_MESSAGE;
        } else if (dataset instanceof ConversationDataset) {
            return DatasetType.CONVERSATION;
        } else {
            return null;
        }
    }

    private static be.unamur.model.Dataset mapAndGetLastVersion(Set<Dataset> datasetList, DatasetFunctionEnum datasetFunction) {
        final List<be.unamur.model.Dataset> datasetListTemp = datasetList.stream()
                .filter(d -> datasetFunction.equals(d.getDatasetFunction()) && d.isLastVersion())
                .map(d -> DatasetDomainToWebMapper.map(d, getType(d)))
                .toList();
        if(datasetListTemp.isEmpty()){
            return null;
        } else {
            return datasetListTemp.get(0);
        }
    }
}
