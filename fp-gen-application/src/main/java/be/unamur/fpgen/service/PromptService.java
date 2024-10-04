package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.exception.PromptNotFoundException;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.prompt.PromptStatusEnum;
import be.unamur.fpgen.repository.PromptRepository;
import be.unamur.model.PromptCreation;
import be.unamur.model.PromptUpdate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class PromptService {
    private final AuthorService authorService;
    private final PromptRepository promptRepository;

    public PromptService(AuthorService authorService, PromptRepository promptRepository) {
        this.authorService = authorService;
        this.promptRepository = promptRepository;
    }

    @Transactional
    public Prompt create(final PromptCreation command) {
        final Author author = authorService.getAuthorById(command.getAuthorId());
        final Integer lastVersion = findMaxVersionByType(MessageTypeWebToDomainMapper.map(command.getCategory()));

        return promptRepository.savePrompt(
                Prompt.newBuilder()
                        .withAuthor(author)
                        .withDefaultPrompt(false)
                        .withType(MessageTypeWebToDomainMapper.map(command.getCategory()))
                        .withStatus(PromptStatusEnum.WAITING_ANALYSE)
                        .withUserPrompt(command.getUserContent())
                        .withSystemPrompt(command.getSystemContent())
                        .withVersion(lastVersion + 1)
                        .build());
    }

    @Transactional
    public Prompt findById(UUID id) {
        return promptRepository.findPromptBId(id)
                .orElseThrow(() -> PromptNotFoundException.withId(id));
    }

    @Transactional
    public Prompt updatePrompt(final UUID id, final PromptUpdate promptUpdate) {
        final Prompt currentPrompt = findById(id);
        currentPrompt.updateSystemPrompt(promptUpdate.getSystemContent());
        currentPrompt.updateUserPrompt(promptUpdate.getUserContent());
        promptRepository.updatePrompt(currentPrompt);
        return currentPrompt;
    }

    @Transactional
    public void setDefaultPrompt(UUID id) {
        promptRepository.setDefaultPrompt(id);
    }

    @Transactional
    public Prompt getDefaultPrompt(final MessageTypeEnum type) {
        return promptRepository.getDefaultPrompt(type)
                .orElseThrow(PromptNotFoundException::withDefaultPrompt);
    }

    @Transactional
    public void updatePromptStatus(UUID id, PromptStatusEnum status) {
        promptRepository.updatePromptStatus(id, status);
    }

    @Transactional
    public List<Prompt> findAllPromptsByType(MessageTypeEnum type) {
        return promptRepository.findAllPromptsByType(type, PromptStatusEnum.VALIDATED);
    }

    @Transactional
    public List<Prompt> findAllPromptsByStatus(PromptStatusEnum status) {
        return promptRepository.findAllPromptsByStatus(status);
    }

    @Transactional
    public Integer findMaxVersionByType(MessageTypeEnum type) {
        return promptRepository.findMaxVersionByType(type);
    }

    @Transactional
    public Prompt findByTypeAndVersion(MessageTypeEnum type, Integer version) {
        return promptRepository.findPromptByTypeAndVersion(type, version)
                .orElseThrow(() -> PromptNotFoundException.withTypeAndVersion(type.name(), version));
    }
}
