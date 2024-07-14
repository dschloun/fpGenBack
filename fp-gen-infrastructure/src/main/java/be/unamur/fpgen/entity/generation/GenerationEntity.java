package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;

import javax.persistence.*;
import java.io.Serial;

@Entity(name = "generation")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind", discriminatorType = DiscriminatorType.STRING)
public class GenerationEntity extends BaseUuidEntity {
    @Serial
    private static final long serialVersionUID = -4712056697432095738L;

    // members
    private String generationId;
    private AuthorEntity author;
    private String details;
    private boolean batch;
    //private DatasetEntity dataset;

    // getters and setters
    @Column(name = "generation_id", nullable = false)
    public String getGenerationId() {
        return generationId;
    }

    public void setGenerationId(String generationId) {
        this.generationId = generationId;
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    @Column(name = "batch")
    public boolean isBatch() {
        return batch;
    }

    public void setBatch(boolean batch) {
        this.batch = batch;
    }

//    @ManyToOne
//    @JoinColumn(name = "dataset_id")
//    public DatasetEntity getDataset() {
//        return dataset;
//    }
//
//    public void setDataset(DatasetEntity dataset) {
//        this.dataset = dataset;
//    }
}
