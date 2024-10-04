package be.unamur.fpgen.repository.prompt;

import be.unamur.fpgen.entity.PromptEntity;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaPromptRepositoryCRUD extends JpaRepository<PromptEntity, UUID> {
    List<PromptEntity> findAllByType(MessageTypeEnum type);
}
