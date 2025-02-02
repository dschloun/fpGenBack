package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.message.ConversationMessage;
import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.model.GenerationCreation;

import java.util.UUID;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class ConversationMessageCreationWebToDomainMapper {

    public static ConversationMessage map(final GenerationCreation web, final Interlocutor sender, final Interlocutor receiver, final UUID conversationId){
        return ConversationMessage.newBuilder()
                .withConversationId(conversationId)
                .withTopic(MessageTopicWebToDomainMapper.map(web.getTopic()))
                .withType(MessageTypeWebToDomainMapper.map(web.getType()))
                .withSender(sender)
                .withReceiver(receiver)
                .build();
    }
}
