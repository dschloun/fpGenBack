package be.unamur.fpgen.repository;

import be.unamur.fpgen.notification.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {

    List<Notification> findByReceiverId(UUID receiverId);

    Optional<Notification> findById(UUID notificationId);

    void update(Notification notification);

    void deleteById(UUID notificationId);

    Notification createNotification(Notification notification);

    boolean existsUnreadNotificationByReceiverId(UUID receiverId);
}
