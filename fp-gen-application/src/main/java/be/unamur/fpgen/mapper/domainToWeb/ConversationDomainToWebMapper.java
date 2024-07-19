package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.Conversation;

public class ConversationDomainToWebMapper {
    public static Conversation map(be.unamur.fpgen.conversation.Conversation domain) {
        return new Conversation()
                .id(domain.getId())
                .generationId(domain.getGenerationId())
                .messageType(MessageTypeDomainToWebMapper.map(domain.getType()))
                .messageTopic(MessageTopicDomainToWebMapper.map(domain.getTopic()))
                .maxInteractionNumber(domain.getMaxInteractionNumber())
                .minInteractionNumber(domain.getMinInteractionNumber())
                .messages(MapperUtil.mapSet(domain.getConversationMessageList(), ConversationMessageDomainToWebMapper::map).stream().toList());
    }
}
