package be.unamur.fpgen.web;

import be.unamur.api.NotificationApi;
import be.unamur.model.Notification;
import be.unamur.model.NotificationStatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Controller
public class NotificationController implements NotificationApi {
    @Override
    public ResponseEntity<Void> deleteNotificationStatus(UUID notificationId) {
        return NotificationApi.super.deleteNotificationStatus(notificationId);
    }

    @Override
    public ResponseEntity<Notification> getNotificationById(UUID notificationId) {
        return NotificationApi.super.getNotificationById(notificationId);
    }

    @Override
    public ResponseEntity<List<Notification>> getNotificationsByAuthor(UUID authorId) {
        return NotificationApi.super.getNotificationsByAuthor(authorId);
    }

    @Override
    public ResponseEntity<Void> updateNotificationStatus(UUID notificationId, @NotNull @Valid NotificationStatusEnum notificationStatus) {
        return NotificationApi.super.updateNotificationStatus(notificationId, notificationStatus);
    }

    @Override
    public ResponseEntity<Boolean> existsUnreadNotificationsByAuthor(UUID authorId) {
        return NotificationApi.super.existsUnreadNotificationsByAuthor(authorId);
    }
}
