package be.unamur.fpgen.entity.project;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.project.ProjectTypeEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project is composed of 3 datasets (TRAINING, TEST, VALIDATION) and is created by an Author.
 * it has an organisation, a businessId, a name and a description.
 */
@Entity
@Table(name = "project")
public class ProjectEntity extends BaseUuidEntity {
    // members
    private ProjectTypeEnum type;
    private String name;
    private String description;
    private String organisation;
    private String businessId;
    private AuthorEntity author;
    private Set<DatasetEntity> datasetList = new HashSet<>();

    // getters and setters

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public ProjectTypeEnum getType() {
        return type;
    }

    public void setType(ProjectTypeEnum type) {
        this.type = type;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "organization", nullable = false)
    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    @Column(name = "business_id", nullable = false)
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    public Set<DatasetEntity> getDatasetList() {
        return datasetList;
    }

    public void setDatasetList(Set<DatasetEntity> datasetList) {
        this.datasetList = datasetList;
    }
}
