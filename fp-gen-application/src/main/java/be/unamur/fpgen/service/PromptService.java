package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.exception.PromptNotFoundException;
import be.unamur.fpgen.mapper.webToDomain.DatasetTypeWebToDomainMapper;
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
import java.util.Optional;
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
        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());
        final Integer lastVersion = findMaxVersionByDatasetTypeAndMessageType(DatasetTypeWebToDomainMapper.map(command.getDatasetType()), MessageTypeWebToDomainMapper.map(command.getMessageType()));

        return promptRepository.savePrompt(
                Prompt.newBuilder()
                        .withAuthor(author)
                        .withDefaultPrompt(false)
                        .withDatasetType(DatasetTypeWebToDomainMapper.map(command.getDatasetType()))
                        .withMessageType(MessageTypeWebToDomainMapper.map(command.getMessageType()))
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
    public Prompt getDefaultPrompt(final DatasetTypeEnum datasetType, final MessageTypeEnum messageType) {
        return promptRepository.getDefaultPrompt(datasetType, messageType)
                .orElseThrow(PromptNotFoundException::withDefaultPrompt);
    }

    @Transactional
    public void updatePromptStatus(UUID id, PromptStatusEnum status) {
        promptRepository.updatePromptStatus(id, status);
    }

    @Transactional
    public List<Prompt> findAllPromptsByDatasetTypeAndMessageType(DatasetTypeEnum datasetType, MessageTypeEnum messageType) {
        return promptRepository.ByDatasetTypeAndMessageType(datasetType, messageType, PromptStatusEnum.VALIDATED);
    }

    @Transactional
    public List<Prompt> findAllPromptsByStatus(PromptStatusEnum status) {
        return promptRepository.findAllPromptsByStatus(status);
    }

    @Transactional
    public Integer findMaxVersionByDatasetTypeAndMessageType(DatasetTypeEnum datasetType, MessageTypeEnum messageType) {
        return promptRepository.findMaxVersionByDatasetTypeAndMessageType(datasetType, messageType);
    }

    @Transactional
    public Optional<Prompt> findByDatasetTypeAndMessageTypeAndVersion(DatasetTypeEnum datasetType, MessageTypeEnum messageType, Integer version) {
        return promptRepository.findPromptByDatasetTypeAndMessageTypeAndVersion(datasetType, messageType, version);
               // .orElseThrow(() -> PromptNotFoundException.withTypeAndVersion(messageType.name(), version));
    }
}
