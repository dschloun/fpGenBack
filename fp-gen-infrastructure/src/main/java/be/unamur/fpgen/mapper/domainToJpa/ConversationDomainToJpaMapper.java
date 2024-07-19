package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;

import java.util.Objects;

public class ConversationDomainToJpaMapper {

    public static ConversationEntity mapForCreate(final Conversation domain, final ConversationGenerationEntity generationEntity){
        final ConversationEntity entity = new ConversationEntity();
        entity.setConversationGeneration(generationEntity);
        entity.setMessageTopic(domain.getTopic());
        entity.setMessageType(domain.getType());
        entity.setMaxInteractionNumber(domain.getMaxInteractionNumber());
        entity.setMinInteractionNumber(domain.getMinInteractionNumber());

        return entity;
    }
}
