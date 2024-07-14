package be.unamur.fpgen.entity.dataset;

import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "CONVERSATION_DATASET")
public class ConversationDatasetEntity extends DatasetEntity {

    // members
    private Set<ConversationGenerationEntity> conversationGenerationList;

    @OneToMany(mappedBy = "conversationDataset")
    public Set<ConversationGenerationEntity> getConversationGenerationList() {
        return conversationGenerationList;
    }

    public void setConversationGenerationList(Set<ConversationGenerationEntity> conversationGenerationList) {
        this.conversationGenerationList = conversationGenerationList;
    }
}
