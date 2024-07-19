package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.model.GenerationCreation;

public class ConversationCreationWebToDomainMapper {

    public static Conversation map(final GenerationCreation web, final int minInteractionNumber, final int maxInteractionNumber) {
        return Conversation.newBuilder()
                .withTopic(MessageTopicWebToDomainMapper.map(web.getTopic()))
                .withType(MessageTypeWebToDomainMapper.map(web.getType()))
                .withMaxInteractionNumber(maxInteractionNumber)
                .withMinInteractionNumber(minInteractionNumber)
                .build();
    }
}
