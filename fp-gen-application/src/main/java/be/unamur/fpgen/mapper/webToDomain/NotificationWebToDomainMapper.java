package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.notification.NotificationStatus;
import be.unamur.model.NotificationStatusEnum;

import java.util.Optional;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class NotificationWebToDomainMapper {

    public static NotificationStatus map(NotificationStatusEnum web){
        return Optional.ofNullable(web)
                .map(NotificationStatusEnum::name)
                .map(NotificationStatus::valueOf)
                .orElse(null);
    }
}
