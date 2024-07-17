package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaConversationMessageRepositoryCRUD extends JpaRepository<ConversationInstantMessageEntity, UUID>{
}
