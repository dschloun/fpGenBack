package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import be.unamur.fpgen.entity.interlocutor.InterlocutorEntity;
import be.unamur.fpgen.message.ConversationMessage;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class ConversationInstantMessageDomainToJpaMapper {

    public static ConversationInstantMessageEntity mapForCreate(final ConversationMessage domain,
                                                                final ConversationEntity conversation,
                                                                final InterlocutorEntity interlocutor1,
                                                                final InterlocutorEntity interlocutor2){
        final ConversationInstantMessageEntity entity = new ConversationInstantMessageEntity();
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        entity.setConversation(conversation);
        entity.setSender(interlocutor1);
        entity.setReceiver(interlocutor2);
        entity.setOrderNumber(domain.getOrderNumber());
        return entity;
    }

    public static ConversationInstantMessageEntity mapForCreate(final ConversationMessage domain,
                                                                final ConversationEntity conversation){
        final ConversationInstantMessageEntity entity = new ConversationInstantMessageEntity();
        entity.setTopic(domain.getTopic());
        entity.setType(domain.getType());
        entity.setContent(domain.getContent());
        entity.setConversation(conversation);
        entity.setSender(InterlocutorDomainToJpaMapper.map(domain.getSender()));
        entity.setReceiver(InterlocutorDomainToJpaMapper.map(domain.getReceiver()));
        entity.setOrderNumber(domain.getOrderNumber());
        return entity;
    }
}
