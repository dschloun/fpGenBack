package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaConversationGenerationCRUD extends JpaRepository<ConversationGenerationEntity, UUID> {
}
