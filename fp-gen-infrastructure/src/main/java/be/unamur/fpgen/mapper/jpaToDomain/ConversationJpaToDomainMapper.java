package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.utils.MapperUtil;

import java.util.Objects;

public class ConversationJpaToDomainMapper {

    public static Conversation map(final ConversationEntity entity){
        if (Objects.isNull(entity)){
            return null;
        }

        return Conversation.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withType(entity.getMessageType())
                .withTopic(entity.getMessageTopic())
                .withMaxInteractionNumber(entity.getMaxInteractionNumber())
                .withMinInteractionNumber(entity.getMinInteractionNumber())
                .withConversationMessageList(MapperUtil.mapSet(entity.getMessageSet(), ConversationInstantMessageJpaToDomainMapper::map))
                .build();
    }
}
