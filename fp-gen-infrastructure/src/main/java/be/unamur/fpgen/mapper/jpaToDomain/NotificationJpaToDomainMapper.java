package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.notification.NotificationEntity;
import be.unamur.fpgen.notification.Notification;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class NotificationJpaToDomainMapper {

    public static Notification map(final NotificationEntity entity){
        return Notification.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withSender(AuthorJpaToDomainMapper.map(entity.getSender()))
                .withReceiver(AuthorJpaToDomainMapper.map(entity.getReceiver()))
                .withStatus(entity.getStatus())
                .withMessage(entity.getMessage())
                .build();
    }
}
