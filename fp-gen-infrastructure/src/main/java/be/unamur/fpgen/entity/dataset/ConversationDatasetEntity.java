package be.unamur.fpgen.entity.dataset;

import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * inherit from class DatasetEntity
 * add the adapted generation set (conversation)
 */
@Entity
@DiscriminatorValue(value = "CONVERSATION_DATASET")
public class ConversationDatasetEntity extends DatasetEntity {

    // members
    private Set<ConversationGenerationEntity> conversationGenerationList = new HashSet<>();;

    @ManyToMany
    @JoinTable(name = "dataset_generation_join_table",
            joinColumns = @JoinColumn(name = "dataset_id"),
            inverseJoinColumns = @JoinColumn(name = "generation_id"))
    public Set<ConversationGenerationEntity> getConversationGenerationList() {
        return conversationGenerationList;
    }

    public void setConversationGenerationList(Set<ConversationGenerationEntity> conversationGenerationList) {
        this.conversationGenerationList = conversationGenerationList;
    }

    public int countConversations() {
        if (this.getConversationGenerationList() == null || this.getConversationGenerationList().isEmpty()) {
            return 0;
        }

        return this.getConversationGenerationList().stream()
                .filter(generation -> generation.getConversationList() != null)
                .mapToInt(generation -> generation.getConversationList().size())
                .sum();
    }
}
