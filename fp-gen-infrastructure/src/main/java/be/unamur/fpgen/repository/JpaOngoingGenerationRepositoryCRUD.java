package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOngoingGenerationRepositoryCRUD extends JpaRepository<OngoingGenerationEntity, UUID> {
}
