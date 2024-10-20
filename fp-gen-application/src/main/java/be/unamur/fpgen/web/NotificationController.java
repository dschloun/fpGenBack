package be.unamur.fpgen.web;

import be.unamur.api.NotificationApi;
import be.unamur.fpgen.mapper.domainToWeb.NotificationDomainToWebMapper;
import be.unamur.fpgen.notification.NotificationStatus;
import be.unamur.fpgen.service.NotificationService;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.Notification;
import be.unamur.model.NotificationCreation;
import be.unamur.model.NotificationStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Controller
public class NotificationController implements NotificationApi {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public ResponseEntity<Void> deleteNotificationStatus(UUID notificationId) {
        notificationService.deleteById(notificationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Notification> getNotificationById(UUID notificationId) {
        return new ResponseEntity<>(NotificationDomainToWebMapper.map(notificationService.findById(notificationId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Notification>> getNotificationsByAuthor(UUID authorId) {
        return new ResponseEntity<>(MapperUtil.mapList(notificationService.findByReceiverId(authorId), NotificationDomainToWebMapper::map), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateNotificationStatus(UUID notificationId, @NotNull @Valid NotificationStatusEnum notificationStatus) {
        notificationService.updateStatus(notificationId, NotificationStatus.valueOf(notificationStatus.name()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Boolean> existsUnreadNotificationsByAuthor(UUID authorId) {
        return new ResponseEntity<>(notificationService.existsUnreadNotificationByReceiverID(authorId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Notification> createNotification(@Valid NotificationCreation notificationCreation) {
        return new ResponseEntity<>(NotificationDomainToWebMapper.map(notificationService.create(notificationCreation)), HttpStatus.CREATED);
    }
}
