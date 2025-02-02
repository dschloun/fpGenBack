package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;
import be.unamur.fpgen.project.Project;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class ProjectDomainToJpaMapper {
    public static ProjectEntity mapForCreate(final Project domain, final AuthorEntity authorEntity) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setType(domain.getType());
        projectEntity.setName(domain.getName());
        projectEntity.setDescription(domain.getDescription());
        projectEntity.setOrganisation(domain.getOrganisation());
        projectEntity.setBusinessId(domain.getBusinessId());
        projectEntity.setAuthor(authorEntity);
        projectEntity.setDatasetList(mapDatasets(domain.getDatasetList(), authorEntity, projectEntity));
        return projectEntity;

    }

    private static Set<DatasetEntity> mapDatasets(Set<Dataset> dataset, AuthorEntity author, ProjectEntity projectEntity) {
        Set<DatasetEntity> datasets = new HashSet<>();
        dataset.forEach(ds -> {
                datasets.add(DataSetDomainToJpaMapper.mapForCreate(ds, author, projectEntity));
        });

        return datasets;
    }
}
