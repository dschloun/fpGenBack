package be.unamur.fpgen.repository;

import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.Prompt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromptRepository {
    Optional<Prompt> findPromptBId(UUID id);

    Optional<Prompt> findPromptByVersion(Integer version);

    void validatePrompt(UUID id);

    void setDefaultPrompt(UUID id);

    List<Prompt> findAllPromptsByType(MessageTypeEnum type);

    Prompt savePrompt(Prompt prompt);
}
