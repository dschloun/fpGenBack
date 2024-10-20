package be.unamur.fpgen.repository.ongoing;

import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaOngoingGenerationRepositoryCRUD extends JpaRepository<OngoingGenerationEntity, UUID> {
    List<OngoingGenerationEntity> findAllByStatus(OngoingGenerationStatus status);
}
