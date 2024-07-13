package be.unamur.fpgen.mapper;

import be.unamur.fpgen.instant_message.InstantMessage;
import be.unamur.model.InstantMessageCreation;

public class InstantMessageWebToDomainMapper {


    public static InstantMessage mapForCreate(final InstantMessageCreation web,
                                               final String content){
        return InstantMessage.newBuilder()
                .withContent(content)
                .withTopic(MessageTopicWebToDomainMapper.map(web.getMessageTopic()))
                .withType(MessageTypeWebToDomainMapper.map(web.getMessageType()))
                .withBatch(web.getQuantity() > 1)
                .build();
    }
}
