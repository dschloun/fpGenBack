package be.unamur.fpgen.repository.generation;

import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaConversationGenerationRepositoryCRUD extends JpaRepository<ConversationGenerationEntity, UUID> {
}
