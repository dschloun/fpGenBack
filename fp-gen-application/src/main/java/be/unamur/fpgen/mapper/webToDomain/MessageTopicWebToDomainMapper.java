package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.model.MessageTopic;

import java.util.Optional;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class MessageTopicWebToDomainMapper {

    public static MessageTopicEnum map(final MessageTopic web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(MessageTopicEnum::valueOf)
                .orElse(null);
    }
}
