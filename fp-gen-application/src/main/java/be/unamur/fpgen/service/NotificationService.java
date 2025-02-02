package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.exception.NotificationNotFoundException;
import be.unamur.fpgen.notification.Notification;
import be.unamur.fpgen.notification.NotificationStatus;
import be.unamur.fpgen.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service class for managing notifications.
 */
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AuthorService authorService;

    public NotificationService(NotificationRepository notificationRepository, AuthorService authorService) {
        this.notificationRepository = notificationRepository;
        this.authorService = authorService;
    }

    /**
     * Find all notifications for the current user.
     * @return a list of notifications
     */
    @Transactional(readOnly = true)
    public List<Notification> findNotifications(){
        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());
        return notificationRepository.findByReceiverId(author.getId());
    }

    /**
     * find a notification by its id
     * @param notificationId
     * @return notification throw NotificationNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public Notification findById(UUID notificationId){
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> NotificationNotFoundException.withId(notificationId));
    }

    /**
     * Update the status of a notification
     * @param notificationId
     * @param status
     */
    @Transactional
    public void updateStatus(UUID notificationId, NotificationStatus status){
        final Notification notification = findById(notificationId);
        notification.updateStatus(status);
        notificationRepository.update(notification);
    }

    /**
     * Delete a notification by its id
     * @param notificationId
     */
    @Transactional
    public void deleteById(UUID notificationId){
        notificationRepository.deleteById(notificationId);
    }

    /**
     * Create a new notification
     * @param authorReceiverId
     * @param message
     * @return the created notification
     */
    @Transactional
    public Notification create(UUID authorReceiverId, String message){
        final Author sender = authorService.getAuthorByTrigram(Objects.nonNull(UserContextHolder.getContext().getTrigram()) ? UserContextHolder.getContext().getTrigram() : "SYSTEM");
        final Author receiver = authorService.getAuthorById(authorReceiverId);

        final Notification notification = Notification.newBuilder()
                .withSender(sender)
                .withReceiver(receiver)
                .withMessage(message)
                .withStatus(NotificationStatus.UNREAD)
                .build();

       return notificationRepository.createNotification(notification);
    }

    /**
     * Check if the current user has unread notifications
     * @return true if the current user has unread notifications
     */
    @Transactional(readOnly = true)
    public boolean existsUnreadNotificationByReceiverID(){
        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());
        return notificationRepository.existsUnreadNotificationByReceiverId(author.getId());
    }
}
