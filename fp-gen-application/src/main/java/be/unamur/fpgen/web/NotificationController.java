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

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * This rest controller class is the implementation of the NotificationApi interface.
 * It is used to manage the notifications of the application.
 */
@Controller
public class NotificationController implements NotificationApi {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * This method is used to delete a notification by its id.
     * @param notificationId the id of the notification to delete
     * @return a response entity with a status code
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> deleteNotificationStatus(UUID notificationId) {
        notificationService.deleteById(notificationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * This method is used to get a notification by its id.
     * @param notificationId the id of the notification to get
     * @return a response entity with the notification
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Notification> getNotificationById(UUID notificationId) {
        return new ResponseEntity<>(NotificationDomainToWebMapper.map(notificationService.findById(notificationId)), HttpStatus.OK);
    }

    /**
     * This method is used to get all the notifications.
     * @return a response entity with a list of notifications
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<List<Notification>> getNotifications() {
        return new ResponseEntity<>(MapperUtil.mapList(notificationService.findNotifications(), NotificationDomainToWebMapper::map), HttpStatus.OK);
    }

    /**
     * This method is used to update the status of a notification.
     * @param notificationId the id of the notification to update
     * @param notificationStatus the new status of the notification
     * @return a response entity with a status code
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Void> updateNotificationStatus(UUID notificationId, @NotNull @Valid NotificationStatusEnum notificationStatus) {
        notificationService.updateStatus(notificationId, NotificationStatus.valueOf(notificationStatus.name()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * check if a user has unread notifications
     * @return true if the user has unread notifications, false otherwise
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Boolean> existsUnreadNotifications() {
        return new ResponseEntity<>(notificationService.existsUnreadNotificationByReceiverID(), HttpStatus.OK);
    }

    /**
     * This method is used to create a notification.
     * @param notificationCreation the notification to create
     * @return a response entity with the created notification
     */
    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<Notification> createNotification(@Valid NotificationCreation notificationCreation) {
        return new ResponseEntity<>(NotificationDomainToWebMapper.map(notificationService.create(notificationCreation.getAuthorReceiverId(), notificationCreation.getMessage())), HttpStatus.CREATED);
    }
}
