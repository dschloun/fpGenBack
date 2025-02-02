package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.utils.MapperUtil;

import java.util.Objects;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class ConversationJpaToDomainMapper {

    public static Conversation map(final ConversationEntity entity){
        if (Objects.isNull(entity)){
            return null;
        }

        return Conversation.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withType(entity.getType())
                .withTopic(entity.getTopic())
                .withMaxInteractionNumber(entity.getMaxInteractionNumber())
                .withMinInteractionNumber(entity.getMinInteractionNumber())
                .withConversationMessageList(MapperUtil.mapSet(entity.getMessageSet(), ConversationInstantMessageJpaToDomainMapper::map))
                .withHash(entity.getHash())
                .build();
    }
}
