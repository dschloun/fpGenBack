package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.notification.NotificationStatus;
import be.unamur.model.Notification;
import be.unamur.model.NotificationStatusEnum;
import be.unamur.model.SenderContact;

import java.util.Optional;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class NotificationDomainToWebMapper {

    public static Notification map(final be.unamur.fpgen.notification.Notification domain){
        return new Notification()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .message(domain.getMessage())
                .status(map(domain.getStatus()))
                .sender(map(domain.getSender()))
                .senderName(domain.getSender().getFirstName() + " " + domain.getSender().getLastName());
    }

    public static SenderContact map(final Author sender){
        return new SenderContact()
                .id(sender.getId())
                .email(sender.getEmail())
                .phoneNumber(sender.getPhoneNumber());
    }

    public static NotificationStatusEnum map(NotificationStatus domain){
        return Optional.ofNullable(domain)
                .map(NotificationStatus::name)
                .map(NotificationStatusEnum::fromValue)
                .orElse(null);
    }
}
