package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.model.MessageTopic;

import java.util.Optional;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class MessageTopicDomainToWebMapper {

    public static MessageTopic map(final MessageTopicEnum domain){
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(MessageTopic::valueOf)
                .orElse(null);
    }
}
