package be.unamur.fpgen.repository.notification;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.notification.NotificationEntity;
import be.unamur.fpgen.mapper.domainToJpa.NotificationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.NotificationJpaToDomainMapper;
import be.unamur.fpgen.notification.Notification;
import be.unamur.fpgen.notification.NotificationStatus;
import be.unamur.fpgen.repository.MessageRepository;
import be.unamur.fpgen.repository.NotificationRepository;
import be.unamur.fpgen.repository.author.JpaAuthorRepositoryCRUD;
import be.unamur.fpgen.utils.MapperUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * See the specifications in the {@link NotificationRepository} interface.
 */
@Repository
public class JpaNotificationRepository implements NotificationRepository {

    private final JpaNotificationRepositoryCRUD jpaNotificationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaNotificationRepository(JpaNotificationRepositoryCRUD jpaNotificationRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaNotificationRepositoryCRUD = jpaNotificationRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public List<Notification> findByReceiverId(UUID receiverId) {
        return MapperUtil.mapList(jpaNotificationRepositoryCRUD.findAllByReceiverId(receiverId), NotificationJpaToDomainMapper::map);
    }

    @Override
    public Optional<Notification> findById(UUID notificationId) {
        return jpaNotificationRepositoryCRUD.findById(notificationId).map(NotificationJpaToDomainMapper::map);
    }

    @Override
    public void update(Notification notification) {
        final NotificationEntity entity = jpaNotificationRepositoryCRUD.findById(notification.getId()).orElseThrow();
        jpaNotificationRepositoryCRUD.save(NotificationDomainToJpaMapper.mapForUpdate(notification, entity));
    }

    @Override
    public void deleteById(UUID notificationId) {
        jpaNotificationRepositoryCRUD.deleteById(notificationId);
    }

    @Override
    public Notification createNotification(Notification notification) {
        final AuthorEntity sender = jpaAuthorRepositoryCRUD.getReferenceById(notification.getSender().getId());
        final AuthorEntity receiver = jpaAuthorRepositoryCRUD.getReferenceById(notification.getReceiver().getId());

        return NotificationJpaToDomainMapper.map(jpaNotificationRepositoryCRUD.save(NotificationDomainToJpaMapper.mapForCreate(notification, sender, receiver)));
    }

    @Override
    public boolean existsUnreadNotificationByReceiverId(UUID receiverId) {
        return jpaNotificationRepositoryCRUD.existsByStatusAndReceiverId(NotificationStatus.UNREAD, receiverId);
    }
}
