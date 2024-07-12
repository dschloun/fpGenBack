package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.BaseUuidEntity;
import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.generation.GenerationTypeEnum;

import javax.persistence.*;
import java.io.Serial;
import java.util.List;

@Entity(name = "generation")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind", discriminatorType = DiscriminatorType.STRING)
public class GenerationEntity extends BaseUuidEntity {
    @Serial
    private static final long serialVersionUID = -4712056697432095738L;

    // members
    private String generationId;
    private String authorTrigram;
    private String details;
    private GenerationTypeEnum type;
    private boolean batch;
//    private List<InstantMessageEntity> instantMessageList;

    // getters and setters
    @Column(name = "generation_id", nullable = false)
    public String getGenerationId() {
        return generationId;
    }

    public void setGenerationId(String generationId) {
        this.generationId = generationId;
    }

    @Column(name = "author_trigram")
    public String getAuthorTrigram() {
        return authorTrigram;
    }

    public void setAuthorTrigram(final String authorTrigram) {
        this.authorTrigram = authorTrigram;
    }

    @Column(name = "details")
    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public GenerationTypeEnum getType() {
        return type;
    }

    public void setType(final GenerationTypeEnum type) {
        this.type = type;
    }

    @Column(name = "batch")
    public boolean isBatch() {
        return batch;
    }

    public void setBatch(boolean batch) {
        this.batch = batch;
    }

//    @OneToMany(mappedBy = "generation")
//    public List<InstantMessageEntity> getInstantMessageList() {
//        return instantMessageList;
//    }
//
//    public void setInstantMessageList(final List<InstantMessageEntity> instantMessageList) {
//        this.instantMessageList = instantMessageList;
//    }
}
