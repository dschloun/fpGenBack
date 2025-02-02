package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import be.unamur.fpgen.message.ConversationMessage;

import java.util.Objects;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class ConversationInstantMessageJpaToDomainMapper {

    public static ConversationMessage map(final ConversationInstantMessageEntity entity){
        if (Objects.isNull(entity)){
            return null;
        }

        return ConversationMessage.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withContent(entity.getContent())
                .withSender(InterlocutorJpaToDomainMapper.map(entity.getSender()))
                .withReceiver(InterlocutorJpaToDomainMapper.map(entity.getReceiver()))
                .withBatch(entity.getConversation().getConversationGeneration().getQuantity() > 1)
                .withOrderNumber(entity.getOrderNumber())
                .build();
    }
}
