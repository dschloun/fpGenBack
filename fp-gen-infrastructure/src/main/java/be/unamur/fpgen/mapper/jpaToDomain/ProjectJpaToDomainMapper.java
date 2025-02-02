package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;
import be.unamur.fpgen.project.Project;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class ProjectJpaToDomainMapper {

    public static Project mapProject(ProjectEntity projectEntity) {
        return Project.newBuilder()
                .withId(projectEntity.getId())
                .withCreationDate(projectEntity.getCreationDate())
                .withModificationDate(projectEntity.getModificationDate())
                .withType(projectEntity.getType())
                .withName(projectEntity.getName())
                .withDescription(projectEntity.getDescription())
                .withOrganisation(projectEntity.getOrganisation())
                .withBusinessId(projectEntity.getBusinessId())
                .withAuthor(AuthorJpaToDomainMapper.map(projectEntity.getAuthor()))
                .withDatasetList(mapDatasets(projectEntity.getDatasetList()))
                .build();
    }

    private static Set<Dataset> mapDatasets(Set<DatasetEntity> datasetEntities) {
        Set<Dataset> datasets = new HashSet<>();
        datasetEntities.forEach(ds -> {
                datasets.add(DatasetJpaToDomainMapper.map(ds));
        });

        return datasets;
    }
}
