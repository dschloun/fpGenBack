package be.unamur.fpgen.entity.dataset;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * inherit from class DatasetEntity
 * add the adapted generation set (instant messages)
 */
@Entity
@DiscriminatorValue(value = "INSTANT_MESSAGE_DATASET")
public class InstantMessageDatasetEntity extends DatasetEntity {

    // members
    private Set<InstantMessageGenerationEntity> instantMessageGenerationList = new HashSet<>();

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

    public int countMessages() {
        if (this.getInstantMessageGenerationList() == null || this.getInstantMessageGenerationList().isEmpty()) {
            return 0;
        }

        return this.getInstantMessageGenerationList().stream()
                .filter(generation -> generation.getInstantMessageList() != null)
                .mapToInt(generation -> generation.getInstantMessageList().size())
                .sum();
    }
}
