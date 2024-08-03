package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;
import be.unamur.fpgen.project.Project;

import java.util.HashSet;
import java.util.Set;

public class ProjectJpaToDomainMapper {

    public static Project mapProject(ProjectEntity projectEntity) {
        return Project.newBuilder()
                .withId(projectEntity.getId())
                .withCreationDate(projectEntity.getCreationDate())
                .withModificationDate(projectEntity.getModificationDate())
                .withName(projectEntity.getName())
                .withDescription(projectEntity.getDescription())
                .withOrganisation(projectEntity.getOrganisation())
                .withBusinessId(projectEntity.getBusinessId())
                .withAuthor(AuthorJpaToDomainMapper.map(projectEntity.getAuthor()))
                .withDatasetList(mapDatasets(projectEntity.getDatasetList()))
                .build();
    }

    private static Set<AbstractDataset> mapDatasets(Set<DatasetEntity> datasetEntities) {
        Set<AbstractDataset> datasets = new HashSet<>();
        datasetEntities.forEach(ds -> {
            if(ds instanceof InstantMessageDatasetEntity imd){
                datasets.add(InstantMessageDatasetJpaToDomainMapper.mapInstantMessageDataset(imd));
            } else if (ds instanceof ConversationDatasetEntity cd){
                datasets.add(ConversationDatasetJpaToDomainMapper.map(cd));
            } else {
                throw new IllegalArgumentException("Unknown dataset type");
            }
        });

        return datasets;
    }
}
