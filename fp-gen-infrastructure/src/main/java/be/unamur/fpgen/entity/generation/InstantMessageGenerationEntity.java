package be.unamur.fpgen.entity.generation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.io.Serial;

@Entity
@DiscriminatorValue(value = "IMG")
public class InstantMessageGenerationEntity extends GenerationEntity {

        @Serial
        private static final long serialVersionUID = 1L;

        @Embedded
        private InstantMessageGenerationData instantMessageGenerationData;

        // getters and setters
        public InstantMessageGenerationData getInstantMessageGenerationData() {
                return instantMessageGenerationData;
        }

        public void setInstantMessageGenerationData(final InstantMessageGenerationData instantMessageGenerationData) {
                this.instantMessageGenerationData = instantMessageGenerationData;
        }
}
