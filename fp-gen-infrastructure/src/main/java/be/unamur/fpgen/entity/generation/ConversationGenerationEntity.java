package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * inherit from class ConversationEntity
 * add the adapted generation set (conversation)
 * add the adapted dataset set (conversation)
 */
@Entity
@DiscriminatorValue(value = "CMG")
public class ConversationGenerationEntity extends GenerationEntity {

    private Set<ConversationEntity> conversationList = new HashSet<>();
    private Set<ConversationDatasetEntity> conversationDatasetList = new HashSet<>();

    @OneToMany(mappedBy = "conversationGeneration", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<ConversationEntity> getConversationList() {
        return conversationList;
    }

    public void setConversationList(Set<ConversationEntity> conversationList) {
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
