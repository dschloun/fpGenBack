package be.unamur.fpgen.repository.prompt;

import be.unamur.fpgen.dataset.DatasetTypeEnum;
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
    List<PromptEntity> findAllByDatasetTypeAndMessageTypeAndStatusOrderByVersionAsc(DatasetTypeEnum datasetType, MessageTypeEnum messageType, PromptStatusEnum status);

    List<PromptEntity> findAllByStatusOrderByVersionAsc(PromptStatusEnum status);

    Optional<PromptEntity> findByDatasetTypeAndMessageTypeAndVersion(DatasetTypeEnum datasetType, MessageTypeEnum messageType, Integer version);

    Optional<PromptEntity> findByDatasetTypeAndMessageTypeAndDefaultPromptIsTrue(DatasetTypeEnum datasetType, MessageTypeEnum type);

    @Query("SELECT MAX(p.version) FROM PromptEntity p WHERE p.datasetType = :datasetType AND p.messageType = :messageType")
    Integer findMaxVersionByDatasetTypeAndMessageType(@Param("datasetType") DatasetTypeEnum datasetType, @Param("messageType") MessageTypeEnum messageType);
}
