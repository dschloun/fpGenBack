package be.unamur.fpgen.entity.dataset;

import be.unamur.fpgen.dataset.DatasetFunctionEnum;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.entity.project.ProjectEntity;
import be.unamur.fpgen.entity.result.ResultEntity;
import be.unamur.fpgen.entity.statistic.StatisticEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a dataset
 * a dataset as a type (Message or Conversation)
 * a function (TRAINING, TEST, VALIDATION)
 * a descriptive statistic which represent the distribution, size, ...
 */
@Entity
@Table(name = "dataset")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind", discriminatorType = DiscriminatorType.STRING)
public class DatasetEntity extends BaseUuidEntity {

    // members
    private String businessId;
    private Integer version;
    private String name;
    private String description;
    private String comment;
    private AuthorEntity author;
    private DatasetFunctionEnum function;
    private ProjectEntity project;
    private OngoingGenerationEntity ongoingGeneration;
    private StatisticEntity statistic;
    private boolean validated;
    private boolean lastVersion;
    private UUID originalDatasetId;
    private Set<ResultEntity> resultList = new HashSet<>();

    // getters and setters
    @Column(name = "business_id", nullable = false)
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(final String businessId) {
        this.businessId = businessId;
    }

    @Column(name = "version", nullable = false)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "description")
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ongoing_generation_id")
    public OngoingGenerationEntity getOngoingGeneration() {
        return ongoingGeneration;
    }

    public void setOngoingGeneration(OngoingGenerationEntity ongoingGeneration) {
        this.ongoingGeneration = ongoingGeneration;
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "statistic_id", referencedColumnName = "id")
    public StatisticEntity getStatistic() {
        return statistic;
    }

    public void setStatistic(StatisticEntity statistic) {
        this.statistic = statistic;
    }

    @Column(name = "validated", nullable = false)
    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    @Column(name = "last_version", nullable = false)
    public boolean isLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(boolean lastVersion) {
        this.lastVersion = lastVersion;
    }

    @Column(name = "original_dataset_id")
    public UUID getOriginalDatasetId() {
        return originalDatasetId;
    }

    public void setOriginalDatasetId(UUID originalDatasetId) {
        this.originalDatasetId = originalDatasetId;
    }

    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<ResultEntity> getResultList() {
        return resultList;
    }

    public void setResultList(Set<ResultEntity> resultList) {
        this.resultList = resultList;
    }
}
