package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;
import be.unamur.fpgen.project.Project;

import java.util.HashSet;
import java.util.Set;

public class ProjectDomainToJpaMapper {
    public static ProjectEntity mapForCreate(final Project domain, final AuthorEntity authorEntity) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setType(domain.getType());
        projectEntity.setName(domain.getName());
        projectEntity.setDescription(domain.getDescription());
        projectEntity.setOrganisation(domain.getOrganisation());
        projectEntity.setBusinessId(domain.getBusinessId());
        projectEntity.setAuthor(authorEntity);
        projectEntity.setDatasetList(mapDatasets(domain.getDatasetList(), authorEntity));
        return projectEntity;

    }

    private static Set<DatasetEntity> mapDatasets(Set<AbstractDataset> dataset, AuthorEntity author) {
        Set<DatasetEntity> datasets = new HashSet<>();
        dataset.forEach(ds -> {
            if (ds instanceof InstantMessageDataset imd) {
                datasets.add(InstantMessageDataSetDomainToJpaMapper.mapForCreate(imd, author));
            } else if (ds instanceof ConversationDataset cd) {
                datasets.add(ConversationDatasetDomainToJpaMapper.mapForCreate(cd, author));
            } else {
                throw new IllegalArgumentException("Unknown dataset type");
            }
        });

        return datasets;
    }
}
