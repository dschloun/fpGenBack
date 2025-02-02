package be.unamur.fpgen.repository;

import be.unamur.fpgen.notification.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for notifications.
 */
public interface NotificationRepository {

    /**
     * Find all notifications for a given receiver.
     * @param receiverId
     * @return List of notifications
     */
    List<Notification> findByReceiverId(UUID receiverId);

    /**
     * Find a notification by its id.
     * @param notificationId
     * @return a notification empty if not found
     */
    Optional<Notification> findById(UUID notificationId);

    /**
     * Update a notification.
     * @param notification
     */
    void update(Notification notification);

    /**
     * Delete a notification by its id.
     * @param notificationId
     */
    void deleteById(UUID notificationId);

    /**
     * Create a notification.
     * @param notification
     * @return the created notification
     */
    Notification createNotification(Notification notification);

    /**
     * Check if there are unread notifications for a given receiver.
     * @param receiverId
     * @return true if there are unread notifications, false otherwise
     */
    boolean existsUnreadNotificationByReceiverId(UUID receiverId);
}
