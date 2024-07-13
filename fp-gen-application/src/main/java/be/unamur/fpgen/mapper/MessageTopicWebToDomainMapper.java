package be.unamur.fpgen.mapper;

import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.model.MessageTopic;

import java.util.Optional;

public class MessageTopicWebToDomainMapper {

    public static MessageTopicEnum map(final MessageTopic web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(MessageTopicEnum::valueOf)
                .orElseThrow();
    }
}
