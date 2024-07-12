package be.unamur.fpgen.entity.generation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CMG")
public class ConversationGenerationEntity extends GenerationEntity {

    // members
    private ConversationGenerationData conversationGenerationData;

    // getters and setters
    @Embedded
    public ConversationGenerationData getConversationMessageGenerationData() {
        return conversationGenerationData;
    }

    public void setConversationMessageGenerationData(final ConversationGenerationData conversationGenerationData) {
        this.conversationGenerationData = conversationGenerationData;
    }
}
