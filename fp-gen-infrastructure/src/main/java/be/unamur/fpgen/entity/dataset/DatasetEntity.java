package be.unamur.fpgen.entity.dataset;

import be.unamur.fpgen.dataset.DatasetFunctionEnum;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;

import javax.persistence.*;

@Entity
@Table(name = "dataset")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind", discriminatorType = DiscriminatorType.STRING)
public class DatasetEntity extends BaseUuidEntity {

    // members
    private String businessId;
    private String version;
    private String name;
    private String description;
    private String comment;
    private AuthorEntity author;
    private DatasetFunctionEnum function;
    private ProjectEntity project;

    // getters and setters
    @Column(name = "business_id", nullable = false)
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(final String businessId) {
        this.businessId = businessId;
    }

    @Column(name = "version", nullable = false)
    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(final AuthorEntity author) {
        this.author = author;
    }

    @Column(name = "function")
    @Enumerated(EnumType.STRING)
    public DatasetFunctionEnum getFunction() {
        return function;
    }

    public void setFunction(DatasetFunctionEnum kind) {
        this.function = kind;
    }

    @ManyToOne
    @JoinColumn(name = "project_id")
    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }
}
