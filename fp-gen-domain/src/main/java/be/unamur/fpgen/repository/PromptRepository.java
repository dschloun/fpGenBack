package be.unamur.fpgen.repository;

import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.prompt.PromptStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromptRepository {
    Optional<Prompt> findPromptBId(UUID id);

    Optional<Prompt> findPromptByTypeAndVersion(MessageTypeEnum type, Integer version);

    void updatePromptStatus(UUID id, PromptStatusEnum status);

    void setDefaultPrompt(UUID id);

    Optional<Prompt> getDefaultPrompt(MessageTypeEnum type);

    List<Prompt> findAllPromptsByType(MessageTypeEnum type, PromptStatusEnum status);

    List<Prompt> findAllPromptsByStatus(PromptStatusEnum type);

    Prompt savePrompt(Prompt prompt);

    void updatePrompt(Prompt prompt);

    Integer findMaxVersionByType(MessageTypeEnum type);
}
