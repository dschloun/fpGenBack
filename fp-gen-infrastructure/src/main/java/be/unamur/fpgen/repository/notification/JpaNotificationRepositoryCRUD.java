package be.unamur.fpgen.repository.notification;

import be.unamur.fpgen.entity.notification.NotificationEntity;
import be.unamur.fpgen.notification.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaNotificationRepositoryCRUD extends JpaRepository<NotificationEntity, UUID> {
    List<NotificationEntity> findAllByReceiverId(UUID receiverId);

    boolean existsByStatusAndReceiverId(NotificationStatus status, UUID receiverId);


}
