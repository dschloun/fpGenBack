package be.unamur.fpgen.repository.ongoing.item;

import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaOngoingGenerationItemRepositoryCRUD extends JpaRepository<OngoingGenerationItemEntity, UUID> {

    void deleteAllByIdIn(List<UUID> ids);
}
