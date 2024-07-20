package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.model.MessageTopic;

import java.util.Optional;

public class MessageTopicDomainToWebMapper {

    public static MessageTopic map(final MessageTopicEnum domain){
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(MessageTopic::valueOf)
                .orElse(null);
    }
}
