package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class ConversationGenerationData implements Serializable {
    @Serial
    private static final long serialVersionUID = 4455469519017267792L;

    private List<ConversationEntity> conversationList;
    private ConversationDatasetEntity conversationDataset;

    @OneToMany(mappedBy = "conversationGeneration")
    public List<ConversationEntity> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<ConversationEntity> conversationList) {
        this.conversationList = conversationList;
    }

    @ManyToOne
    @JoinColumn(name = "dataset_id")
    public ConversationDatasetEntity getConversationDataset() {
        return conversationDataset;
    }

    public void setConversationDataset(ConversationDatasetEntity conversationDataset) {
        this.conversationDataset = conversationDataset;
    }
}
