package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.dataset.ConversationDatasetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaConversationDatasetRepositoryCRUD extends JpaRepository<ConversationDatasetEntity, UUID>{
}
