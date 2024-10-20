package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.notification.NotificationEntity;
import be.unamur.fpgen.notification.Notification;

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
