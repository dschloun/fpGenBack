package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.notification.NotificationEntity;
import be.unamur.fpgen.notification.Notification;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class NotificationDomainToJpaMapper {
    public static NotificationEntity mapForCreate(final Notification domain, final AuthorEntity sender, final AuthorEntity receiver){
        final NotificationEntity entity = new NotificationEntity();
        entity.setSender(sender);
        entity.setReceiver(receiver);
        entity.setStatus(domain.getStatus());
        entity.setMessage(domain.getMessage());

        return entity;
    }

    public static NotificationEntity mapForUpdate(final Notification domain, final NotificationEntity entity){
        entity.setStatus(domain.getStatus());
        return entity;
    }
}
