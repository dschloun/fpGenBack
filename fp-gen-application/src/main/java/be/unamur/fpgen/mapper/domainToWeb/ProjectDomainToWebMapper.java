package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.dataset.DatasetFunctionEnum;
import be.unamur.model.Project;

import java.util.List;
import java.util.Set;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class ProjectDomainToWebMapper {

    public static Project map(be.unamur.fpgen.project.Project domain) {
        return new Project()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .modificationDate(domain.getModificationDate())
                .name(domain.getName())
                .description(domain.getDescription())
                .organization(domain.getOrganisation())
                .type(ProjectTypeDomainToWebMapper.map(domain.getType()))
                .author(AuthorDomainToWebMapper.map(domain.getAuthor()))
                .trainingDataset(mapAndGetLastVersion(domain.getDatasetList(), DatasetFunctionEnum.TRAINING))
                .testDataset(mapAndGetLastVersion(domain.getDatasetList(), DatasetFunctionEnum.TEST))
                .validationDataset(mapAndGetLastVersion(domain.getDatasetList(), DatasetFunctionEnum.VALIDATION));
    }

    private static be.unamur.model.Dataset mapAndGetLastVersion(Set<Dataset> datasetList, DatasetFunctionEnum datasetFunction) {
        final List<be.unamur.model.Dataset> datasetListTemp = datasetList.stream()
                .filter(d -> datasetFunction.equals(d.getDatasetFunction()) && d.isLastVersion())
                .map(DatasetDomainToWebMapper::map)
                .toList();
        if(datasetListTemp.isEmpty()){
            return null;
        } else {
            return datasetListTemp.get(0);
        }
    }
}
