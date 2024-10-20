package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.notification.NotificationStatus;
import be.unamur.model.Notification;
import be.unamur.model.NotificationStatusEnum;
import be.unamur.model.SenderContact;

import java.util.Optional;

public class NotificationDomainToWebMapper {

    public static Notification map(final be.unamur.fpgen.notification.Notification domain){
        return new Notification()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .message(domain.getMessage())
                .status(map(domain.getStatus()))
                .sender(map(domain.getSender()));
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
