package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.model.InstantMessage;

public class InstantMessageDomainToWebMapper {

    public static InstantMessage map(final be.unamur.fpgen.message.InstantMessage domain){
        return new InstantMessage()
                .id(domain.getId())
                .messageType(MessageTypeDomainToWebMapper.map(domain.getType()))
                .messageTopic(MessageTopicDomainToWebMapper.map(domain.getTopic()))
                .content(domain.getContent());
    }
}
