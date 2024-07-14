package be.unamur.fpgen.entity.dataset;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "INSTANT_MESSAGE_DATASET")
public class InstantMessageDatasetEntity extends DatasetEntity {

    // members
    private Set<InstantMessageGenerationEntity> instantMessageGenerationList;

    // getters and setters
    @ManyToMany
    @JoinTable(name = "dataset_generation_join_table",
            joinColumns = @JoinColumn(name = "dataset_id"),
            inverseJoinColumns = @JoinColumn(name = "generation_id"))
    public Set<InstantMessageGenerationEntity> getInstantMessageGenerationList() {
        return instantMessageGenerationList;
    }

    public void setInstantMessageGenerationList(Set<InstantMessageGenerationEntity> instantMessageGeneration) {
        this.instantMessageGenerationList = instantMessageGeneration;
    }
}
