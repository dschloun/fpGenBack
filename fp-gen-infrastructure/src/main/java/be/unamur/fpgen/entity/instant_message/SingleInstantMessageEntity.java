package be.unamur.fpgen.entity.instant_message;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serial;

/**
 * @overview: SingleInstantMessageEntity is an entity class that represents a single instant message.
 * SingleInstantMessageEntity extends InstantMessageEntity.
 */
@Entity
@DiscriminatorValue(value = "SIM")
public class SingleInstantMessageEntity extends InstantMessageEntity {

    @Serial
    private static final long serialVersionUID = -763345217635016682L;
}
