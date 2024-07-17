package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.instant_message.ConversationMessage;
import be.unamur.model.GenerationCreation;

public class ConversationMessageCreationWebToDomainMapper {

    public static ConversationMessage map(final GenerationCreation web){
        return ConversationMessage.newBuilder()
                .withTopic(MessageTopicWebToDomainMapper.map(web.getTopic()))
                .withType(MessageTypeWebToDomainMapper.map(web.getType()))
                .build();
    }
}
