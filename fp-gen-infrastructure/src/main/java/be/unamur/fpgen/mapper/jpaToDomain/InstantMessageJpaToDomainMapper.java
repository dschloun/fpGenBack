package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.message.InstantMessage;

import java.util.Objects;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class InstantMessageJpaToDomainMapper {

    private InstantMessageJpaToDomainMapper(){
        // left empty intentionally
    }

    /**
     * Maps the given InstantMessageEntity JPA object to a new InstantMessage domain object.
     * @return null if the given entity is null,
     * otherwise a InstantMessage object mapped from the given entity
     */
    public static InstantMessage map(final InstantMessageEntity entity){
        if (Objects.isNull(entity)){
            return null;
        }

        return InstantMessage.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withTopic(entity.getTopic())
                .withType(entity.getType())
                .withContent(entity.getContent())
                .withGenerationId(entity.getInstantMessageGeneration().getGenerationId()) //fixme remove later (double)
                .withGenerationTechniqueId(entity.getInstantMessageGeneration().getId())
                .withGenerationBusinessId(entity.getInstantMessageGeneration().getGenerationId())
                .withBatch(entity.getInstantMessageGeneration().getQuantity() > 1)
                .withHash(entity.getHash())
                .build();
    }
}
