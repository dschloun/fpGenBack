package be.unamur.fpgen.repository.ongoing.item;

import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationItemEntity;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItemStatus;
import be.unamur.fpgen.repository.NotificationRepository;
import be.unamur.fpgen.repository.OngoingGenerationItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * See the specifications in the {@link OngoingGenerationItemRepository} interface.
 */
@Repository
public class JpaOngoingGenerationItemRepository implements OngoingGenerationItemRepository {

    private final JpaOngoingGenerationItemRepositoryCRUD jpaOngoingGenerationItemRepositoryCRUD;

    public JpaOngoingGenerationItemRepository(JpaOngoingGenerationItemRepositoryCRUD jpaOngoingGenerationItemRepositoryCRUD) {
        this.jpaOngoingGenerationItemRepositoryCRUD = jpaOngoingGenerationItemRepositoryCRUD;
    }

    @Override
    public void deleteAllByIdIn(List<UUID> ids) {
        jpaOngoingGenerationItemRepositoryCRUD.deleteAllByIdIn(ids);
    }

    @Override
    public void updateStatus(UUID id, OngoingGenerationItemStatus status) {
        final OngoingGenerationItemEntity entity = jpaOngoingGenerationItemRepositoryCRUD.findById(id).orElseThrow();
        entity.setStatus(status);
        jpaOngoingGenerationItemRepositoryCRUD.save(entity);
    }
}
