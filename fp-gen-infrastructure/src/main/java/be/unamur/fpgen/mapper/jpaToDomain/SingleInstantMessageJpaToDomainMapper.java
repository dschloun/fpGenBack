package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.instant_message.SingleInstantMessageEntity;
import be.unamur.fpgen.instant_message.SingleInstantMessage;

import java.util.Objects;

public class SingleInstantMessageJpaToDomainMapper {

    private SingleInstantMessageJpaToDomainMapper(){
        // left empty intentionally
    }

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
                .build();
    }
}
