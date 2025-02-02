package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationItemEntity;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class OngoingGenerationItemJpaToDomainMapper {

    public static OngoingGenerationItem map(OngoingGenerationItemEntity entity) {
        return OngoingGenerationItem.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withMessageType(entity.getMessageType())
                .withMessageTopic(entity.getMessageTopic())
                .withQuantity(entity.getQuantity())
                .withStatus(entity.getStatus())
                .withPromptId(entity.getPromptId())
                .build();
    }
}