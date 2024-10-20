package be.unamur.fpgen.entity.instant_message;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;

import javax.persistence.*;

/**
 * @overview: InstantMessageEntity is an entity class that represents an instant message.
 * InstantMessageEntity extends AbstractInstantMessageEntity.
 */
@Entity
@DiscriminatorValue(value = "SIM")
public class InstantMessageEntity extends AbstractInstantMessageEntity {

    private InstantMessageGenerationEntity instantMessageGeneration;
    private String hash;

    @ManyToOne
    @JoinColumn(name = "generation_id")
    public InstantMessageGenerationEntity getInstantMessageGeneration() {
        return instantMessageGeneration;
    }

    public void setInstantMessageGeneration(InstantMessageGenerationEntity generation) {
        this.instantMessageGeneration = generation;
    }

    @Column(name = "hash", nullable = false)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
