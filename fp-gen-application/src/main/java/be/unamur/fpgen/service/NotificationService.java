package be.unamur.fpgen.service;

import be.unamur.fpgen.exception.NotificationNotFoundException;
import be.unamur.fpgen.notification.Notification;
import be.unamur.fpgen.notification.NotificationStatus;
import be.unamur.fpgen.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AuthorService authorService;

    public NotificationService(NotificationRepository notificationRepository, AuthorService authorService) {
        this.notificationRepository = notificationRepository;
        this.authorService = authorService;
    }

    @Transactional(readOnly = true)
    public List<Notification> findByReceiverId(UUID receiverId){
        return notificationRepository.findByReceiverId(receiverId);
    }

    @Transactional(readOnly = true)
    public Notification findById(UUID notificationId){
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> NotificationNotFoundException.withId(notificationId));
    }

    @Transactional
    public void updateStatus(UUID notificationId, NotificationStatus status){
        final Notification notification = findById(notificationId);
        notification.updateStatus(status);
        notificationRepository.update(notification);
    }

    @Transactional
    public void deleteById(UUID notificationId){
        notificationRepository.deleteById(notificationId);
    }

    @Transactional
    public void create(Notification notification){
        notificationRepository.createNotification(notification);
    }

    @Transactional(readOnly = true)
    public boolean existsUnreadNotificationByReceiverID(UUID receiverId){
        return notificationRepository.existsUnreadNotificationByReceiverId(receiverId);
    }
}
