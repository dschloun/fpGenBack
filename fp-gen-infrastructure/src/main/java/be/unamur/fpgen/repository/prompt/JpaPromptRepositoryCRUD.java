package be.unamur.fpgen.repository.prompt;

import be.unamur.fpgen.entity.PromptEntity;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.PromptStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaPromptRepositoryCRUD extends JpaRepository<PromptEntity, UUID> {
    List<PromptEntity> findAllByTypeAndStatusOrderByVersionAsc(MessageTypeEnum type, PromptStatusEnum status);

    List<PromptEntity> findAllByStatusOrderByVersionAsc(PromptStatusEnum status);

    Optional<PromptEntity> findByTypeAndVersion(MessageTypeEnum type, Integer version);

    Optional<PromptEntity> findByTypeAndDefaultPromptIsTrue(MessageTypeEnum type);

    @Query("SELECT MAX(p.version) FROM PromptEntity p WHERE p.type = :type")
    Integer findMaxVersionByType(@Param("type") MessageTypeEnum type);
}
