package be.unamur.fpgen.entity.instant_message;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;

import javax.persistence.*;

/**
 * @overview: SingleInstantMessageEntity is an entity class that represents a single instant message.
 * SingleInstantMessageEntity extends InstantMessageEntity.
 */
@Entity
@DiscriminatorValue(value = "SIM")
public class InstantMessageEntity extends AbstractInstantMessageEntity {

    private InstantMessageGenerationEntity instantMessageGeneration;

    @ManyToOne
    @JoinColumn(name = "generation_id")
    public InstantMessageGenerationEntity getInstantMessageGeneration() {
        return instantMessageGeneration;
    }

    public void setInstantMessageGeneration(InstantMessageGenerationEntity generation) {
        this.instantMessageGeneration = generation;
    }
}
