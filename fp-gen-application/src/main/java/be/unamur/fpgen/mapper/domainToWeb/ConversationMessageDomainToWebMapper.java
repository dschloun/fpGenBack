package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.model.ConversationMessage;

public class ConversationMessageDomainToWebMapper {

    public static ConversationMessage map(be.unamur.fpgen.message.ConversationMessage domain) {
        return new ConversationMessage()
                .id(domain.getId())
                .conversationId(domain.getConversationId())
                .sender(InterlocutorDomainToWebMapper.map(domain.getSender()))
                .receiver(InterlocutorDomainToWebMapper.map(domain.getReceiver()))
                .orderNumber(domain.getOrderNumber())
                .content(domain.getContent());
    }
}
