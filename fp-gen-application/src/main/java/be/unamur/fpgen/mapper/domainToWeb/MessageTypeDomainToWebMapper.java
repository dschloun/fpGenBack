package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.instant_message.MessageTypeEnum;
import be.unamur.model.MessageType;

import java.util.Optional;

public class MessageTypeDomainToWebMapper {

    public static MessageType map(final MessageTypeEnum domain){
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(MessageType::valueOf)
                .orElse(null);
    }
}
