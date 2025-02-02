package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.model.GenerationCreation;
import be.unamur.model.MessageTopic;
import be.unamur.model.MessageType;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class ConversationCreationWebToDomainMapper {

    public static Conversation map(final GenerationCreation web, final int minInteractionNumber, final int maxInteractionNumber) {
        return Conversation.newBuilder()
                .withTopic(MessageTopicWebToDomainMapper.map(web.getTopic()))
                .withType(MessageTypeWebToDomainMapper.map(web.getType()))
                .withMaxInteractionNumber(maxInteractionNumber)
                .withMinInteractionNumber(minInteractionNumber)
                .build();
    }

    public static Conversation map(final MessageTypeEnum type, final MessageTopicEnum topic, final int minInteractionNumber, final int maxInteractionNumber) {
        return Conversation.newBuilder()
                .withTopic(topic)
                .withType(type)
                .withMaxInteractionNumber(maxInteractionNumber)
                .withMinInteractionNumber(minInteractionNumber)
                .build();
    }
}
