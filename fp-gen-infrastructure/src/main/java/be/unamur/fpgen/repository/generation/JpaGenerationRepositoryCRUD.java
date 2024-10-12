package be.unamur.fpgen.repository.generation;

import be.unamur.fpgen.entity.generation.GenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaGenerationRepositoryCRUD extends JpaRepository<GenerationEntity, UUID> {
}
