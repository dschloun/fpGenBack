package be.unamur.fpgen.repository;

import be.unamur.fpgen.notification.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository {

    List<Notification> findByAuthorId(UUID authorId);

    Notification findById(UUID notificationId);

    void update(Notification notification);

    void deleteById(UUID notificationId);

    void createNotification(Notification notification);

    boolean existsUnreadNotificationByAuthorId(UUID authorId);
}
