package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.instant_message.MessageTypeEnum;
import be.unamur.model.MessageType;

import java.util.Optional;

public class MessageTypeWebToDomainMapper {

    public static MessageTypeEnum map(final MessageType web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(MessageTypeEnum::valueOf)
                .orElse(null);
    }
}
