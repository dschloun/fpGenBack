package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.instant_message.SingleInstantMessageEntity;
import be.unamur.fpgen.instant_message.SingleInstantMessage;

import java.util.Objects;

/**
 * @overview
 * SingleInstantMessageJpaToDomainMapper is a utility class providing methods to map SingleInstantMessageEntity JPA objects to
 * SingleInstantMessage domain objects.
 */
public class SingleInstantMessageJpaToDomainMapper {

    private SingleInstantMessageJpaToDomainMapper(){
        // left empty intentionally
    }

    /**
     * Maps the given SingleInstantMessageEntity JPA object to a new SingleInstantMessage domain object.
     * @return null if the given entity is null,
     * otherwise a SingleInstantMessage object mapped from the given entity
     */
    public static SingleInstantMessage map(final SingleInstantMessageEntity entity){
        if (Objects.isNull(entity)){
            return null;
        }

        return SingleInstantMessage.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withTopic(entity.getTopic())
                .withType(entity.getType())
                .withContent(entity.getContent())
                .withGenerationId(entity.getGeneration().getGenerationId())
                .withBatch(entity.getGeneration().isBatch())
                .build();
    }
}
