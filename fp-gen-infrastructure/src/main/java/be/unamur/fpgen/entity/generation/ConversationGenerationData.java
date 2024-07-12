package be.unamur.fpgen.entity.generation;

import be.unamur.fpgen.entity.conversation.ConversationEntity;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class ConversationGenerationData implements Serializable {
    @Serial
    private static final long serialVersionUID = 4455469519017267792L;

    private List<ConversationEntity> conversationList;

    @OneToMany(mappedBy = "conversationGeneration")
    public List<ConversationEntity> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<ConversationEntity> conversationList) {
        this.conversationList = conversationList;
    }
}
