package be.unamur.fpgen.entity.instant_message;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;

import javax.persistence.*;
import java.io.Serial;

/**
 * @overview: SingleInstantMessageEntity is an entity class that represents a single instant message.
 * SingleInstantMessageEntity extends InstantMessageEntity.
 */
@Entity
@DiscriminatorValue(value = "SIM")
public class SingleInstantMessageEntity extends InstantMessageEntity {

    private InstantMessageGenerationEntity singleInstantMessageGeneration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instant_message_generation_id")
    public InstantMessageGenerationEntity getSingleInstantMessageGeneration() {
        return singleInstantMessageGeneration;
    }

    public void setSingleInstantMessageGeneration(InstantMessageGenerationEntity generation) {
        this.singleInstantMessageGeneration = generation;
    }
}
