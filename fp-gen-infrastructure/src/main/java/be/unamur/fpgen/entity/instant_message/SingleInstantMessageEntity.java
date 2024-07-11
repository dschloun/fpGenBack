package be.unamur.fpgen.entity.instant_message;

import javax.persistence.Entity;
import java.io.Serial;

@Entity(name = "single_instant_message")
public class SingleInstantMessageEntity extends InstantMessageEntity {

    @Serial
    private static final long serialVersionUID = -763345217635016682L;
}
