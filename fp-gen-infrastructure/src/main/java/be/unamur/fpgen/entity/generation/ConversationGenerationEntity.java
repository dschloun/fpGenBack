package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "CMG")
public class ConversationGenerationEntity extends GenerationEntity {

    private List<ConversationEntity> conversationList;
    private Set<ConversationDatasetEntity> conversationDatasetList;

    @OneToMany(mappedBy = "conversationGeneration")
    public List<ConversationEntity> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<ConversationEntity> conversationList) {
        this.conversationList = conversationList;
    }

    @ManyToMany(mappedBy = "conversationGenerationList")
    public Set<ConversationDatasetEntity> getConversationDatasetList() {
        return conversationDatasetList;
    }

    public void setConversationDatasetList(Set<ConversationDatasetEntity> conversationDatasetList) {
        this.conversationDatasetList = conversationDatasetList;
    }
}
