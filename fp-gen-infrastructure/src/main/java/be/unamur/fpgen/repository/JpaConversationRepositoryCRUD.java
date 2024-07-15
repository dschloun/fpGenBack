package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaConversationRepositoryCRUD extends JpaRepository<ConversationEntity, UUID>{
}
