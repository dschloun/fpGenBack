package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;

import javax.persistence.*;
import java.io.Serial;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "IMG")
public class InstantMessageGenerationEntity extends GenerationEntity {

        private List<InstantMessageEntity> instantMessageList;
        private Set<InstantMessageDatasetEntity> instantMessageDatasetList;

        // getters and setters
        @OneToMany(mappedBy = "instantMessageGeneration")
        public List<InstantMessageEntity> getInstantMessageList() {
                return instantMessageList;
        }

        public void setInstantMessageList(List<InstantMessageEntity> instantMessageList) {
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
