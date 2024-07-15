package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.conversation.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaConversationMessageRepositoryCRUD extends JpaRepository<ConversationEntity, UUID>{
}
