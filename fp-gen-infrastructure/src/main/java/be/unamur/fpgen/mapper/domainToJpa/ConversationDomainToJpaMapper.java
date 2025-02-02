package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.utils.MapperUtil;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class ConversationDomainToJpaMapper {

    public static ConversationEntity mapForCreate(final Conversation domain, final ConversationGenerationEntity generationEntity){
        final ConversationEntity entity = new ConversationEntity();
        entity.setConversationGeneration(generationEntity);
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setMaxInteractionNumber(domain.getMaxInteractionNumber());
        entity.setMinInteractionNumber(domain.getMinInteractionNumber());
        entity.setMessageSet(MapperUtil.mapSet(domain.getConversationMessageList(), c -> ConversationInstantMessageDomainToJpaMapper.mapForCreate(c, entity)));
        entity.setHash(domain.getHash());

        return entity;
    }
}
