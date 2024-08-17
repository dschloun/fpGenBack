package be.unamur.fpgen.repository.generation;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaInstantMessageGenerationRepositoryCRUD extends JpaRepository<InstantMessageGenerationEntity, UUID>{
}
