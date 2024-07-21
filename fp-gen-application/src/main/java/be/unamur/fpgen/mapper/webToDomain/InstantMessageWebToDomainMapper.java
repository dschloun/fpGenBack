package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.message.InstantMessage;
import be.unamur.model.GenerationCreation;

public class InstantMessageWebToDomainMapper {


    public static InstantMessage mapForCreate(final GenerationCreation web,
                                               final String content){
        return InstantMessage.newBuilder()
                .withContent(content)
                .withTopic(MessageTopicWebToDomainMapper.map(web.getTopic()))
                .withType(MessageTypeWebToDomainMapper.map(web.getType()))
                .withBatch(web.getQuantity() > 1)
                .build();
    }
}
