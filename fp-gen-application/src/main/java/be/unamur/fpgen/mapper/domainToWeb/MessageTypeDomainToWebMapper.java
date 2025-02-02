package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.model.MessageType;

import java.util.Optional;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class MessageTypeDomainToWebMapper {

    public static MessageType map(final MessageTypeEnum domain){
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(MessageType::valueOf)
                .orElse(null);
    }
}
