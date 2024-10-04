package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.exception.PromptNotFoundException;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.prompt.PromptStatusEnum;
import be.unamur.fpgen.repository.PromptRepository;
import be.unamur.model.PromptCreation;
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
    public Prompt create(final PromptCreation command, final UUID authorId) {
        final Author author = authorService.getAuthorById(authorId);
        final Integer lastVersion = promptRepository.findMaxVersionByType(MessageTypeWebToDomainMapper.map(command.getCategory()));

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
    public Prompt createPrompt(final Prompt prompt) {
        return promptRepository.savePrompt(prompt);
    }

    @Transactional
    public void updatePrompt(final Prompt prompt) {
        promptRepository.updatePrompt(prompt);
    }

    @Transactional
    public void setDefaultPrompt(UUID id, boolean state) {
        promptRepository.setDefaultPrompt(id, state);
    }

    @Transactional
    public Prompt getDefaultPrompt() {
        return promptRepository.getDefaultPrompt()
                .orElseThrow(PromptNotFoundException::withDefaultPrompt);
    }

    @Transactional
    public void updatePromptStatus(UUID id, PromptStatusEnum status) {
        promptRepository.updatePromptStatus(id, status);
    }

    @Transactional
    public List<Prompt> findAllPromptsByType(MessageTypeEnum type, PromptStatusEnum status) {
        return promptRepository.findAllPromptsByType(type, status);
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
