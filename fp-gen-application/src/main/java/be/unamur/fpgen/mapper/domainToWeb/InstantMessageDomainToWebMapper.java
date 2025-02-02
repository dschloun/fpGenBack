package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.model.InstantMessage;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class InstantMessageDomainToWebMapper {

    public static InstantMessage map(final be.unamur.fpgen.message.InstantMessage domain){
        return new InstantMessage()
                .id(domain.getId())
                .messageType(MessageTypeDomainToWebMapper.map(domain.getType()))
                .messageTopic(MessageTopicDomainToWebMapper.map(domain.getTopic()))
                .content(domain.getContent());
    }
}
