package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.model.MessageType;

import java.util.Optional;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class MessageTypeWebToDomainMapper {

    public static MessageTypeEnum map(final MessageType web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(MessageTypeEnum::valueOf)
                .orElse(null);
    }
}
