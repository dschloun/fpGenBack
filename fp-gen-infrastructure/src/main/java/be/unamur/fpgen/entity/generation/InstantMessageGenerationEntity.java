package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * inherit from class ConversationEntity
 * add the adapted generation set (instant message)
 * add the adapted dataset set (instant message)
 */
@Entity
@DiscriminatorValue(value = "IMG")
public class InstantMessageGenerationEntity extends GenerationEntity {

        private Set<InstantMessageEntity> instantMessageList = new HashSet<>();
        private Set<InstantMessageDatasetEntity> instantMessageDatasetList = new HashSet<>();

        // getters and setters
        @OneToMany(mappedBy = "instantMessageGeneration", cascade = CascadeType.ALL, orphanRemoval = true)
        public Set<InstantMessageEntity> getInstantMessageList() {
                return instantMessageList;
        }

        public void setInstantMessageList(Set<InstantMessageEntity> instantMessageList) {
                this.instantMessageList = instantMessageList;
        }

        @ManyToMany(mappedBy = "instantMessageGenerationList")
        public Set<InstantMessageDatasetEntity> getInstantMessageDatasetList() {
                return instantMessageDatasetList;
        }

        public void setInstantMessageDatasetList(Set<InstantMessageDatasetEntity> instantMessageDatasetList) {
                this.instantMessageDatasetList = instantMessageDatasetList;
        }
}
