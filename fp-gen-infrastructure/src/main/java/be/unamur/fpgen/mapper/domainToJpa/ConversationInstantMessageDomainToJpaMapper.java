package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import be.unamur.fpgen.instant_message.ConversationMessage;

public class ConversationInstantMessageDomainToJpaMapper {

    public static ConversationInstantMessageEntity mapForCreate(final ConversationMessage domain, final ConversationEntity conversation){
        final ConversationInstantMessageEntity entity = new ConversationInstantMessageEntity();
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        entity.setConversation(conversation);
        return entity;
    }
}
