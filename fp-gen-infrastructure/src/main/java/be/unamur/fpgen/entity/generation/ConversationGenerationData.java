package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Embeddable
public class ConversationGenerationData implements Serializable {
    @Serial
    private static final long serialVersionUID = 4455469519017267792L;

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
