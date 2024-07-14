package be.unamur.fpgen.entity.dataset;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "INSTANT_MESSAGE_DATASET")
public class InstantMessageDatasetEntity extends DatasetEntity {

    // members
    private Set<InstantMessageGenerationEntity> instantMessageGeneration;

    // getters and setters

    @OneToMany(mappedBy = "instantMessageDataset")
    public Set<InstantMessageGenerationEntity> getInstantMessageGeneration() {
        return instantMessageGeneration;
    }

    public void setInstantMessageGeneration(Set<InstantMessageGenerationEntity> instantMessageGeneration) {
        this.instantMessageGeneration = instantMessageGeneration;
    }
}
