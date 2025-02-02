package be.unamur.fpgen.entity.generation.ongoing_generation;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents an ongoing generation of a dataset.
 * It's a generation request, it has not been generated yet
 */
@Entity
@Table(name = "ongoing_generation")
public class OngoingGenerationEntity extends BaseUuidEntity {
    private GenerationTypeEnum type;
    private Set<OngoingGenerationItemEntity> itemList = new HashSet<>();
    private AuthorEntity author;
    private OngoingGenerationStatus status;
    private UUID datasetId;
    private Integer minInteractionNumber;
    private Integer maxInteractionNumber;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    public GenerationTypeEnum getType() {
        return type;
    }

    public void setType(GenerationTypeEnum type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "ongoingGeneration", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<OngoingGenerationItemEntity> getItemList() {
        return itemList;
    }

    public void setItemList(Set<OngoingGenerationItemEntity> itemList) {
        this.itemList = itemList;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public OngoingGenerationStatus getStatus() {
        return status;
    }

    public void setStatus(OngoingGenerationStatus status) {
        this.status = status;
    }

    @Column(name = "dataset_id", nullable = true)
    public UUID getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(UUID datasetId) {
        this.datasetId = datasetId;
    }

    @Column(name = "min_interaction_number", nullable = true)
    public Integer getMinInteractionNumber() {
        return minInteractionNumber;
    }

    public void setMinInteractionNumber(Integer minInteractionNumber) {
        this.minInteractionNumber = minInteractionNumber;
    }

    @Column(name = "max_interaction_number", nullable = true)
    public Integer getMaxInteractionNumber() {
        return maxInteractionNumber;
    }

    public void setMaxInteractionNumber(Integer maxInteractionNumber) {
        this.maxInteractionNumber = maxInteractionNumber;
    }
}
