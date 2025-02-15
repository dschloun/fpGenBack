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

/**
 * Service class for managing prompts.
 */
@Service
public class PromptService {
    private final AuthorService authorService;
    private final PromptRepository promptRepository;

    public PromptService(AuthorService authorService, PromptRepository promptRepository) {
        this.authorService = authorService;
        this.promptRepository = promptRepository;
    }

    /**
     * Create a new prompt.
     * Send notification to all administrators to verify the prompt.
     *
     * @param command the prompt creation command
     * @return the created prompt
     */
    @Transactional
    public Prompt create(final PromptCreation command) {
        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());
        final Integer lastVersion = findMaxVersionByDatasetTypeAndMessageType(DatasetTypeWebToDomainMapper.map(command.getDatasetType()), MessageTypeWebToDomainMapper.map(command.getMessageType()));

        final Prompt prompt =  Prompt.newBuilder()
                .withAuthor(author)
                .withDefaultPrompt(false)
                .withDatasetType(DatasetTypeWebToDomainMapper.map(command.getDatasetType()))
                .withMessageType(MessageTypeWebToDomainMapper.map(command.getMessageType()))
                .withStatus(PromptStatusEnum.WAITING_ANALYSE)
                .withUserPrompt(command.getUserContent())
                .withSystemPrompt(command.getSystemContent())
                .withVersion(lastVersion + 1)
                .withMotivation(command.getMotivation())
                .build();

        authorService.sendNotificationToAllAdministrators(String.format
                ("VERIFY PROMPT: prompt %s %s from author %s %s with trigram %s", prompt.getDatasetType(), prompt.getMessageType(), author.getFirstName(), author.getLastName(), author.getTrigram()));

        return promptRepository.savePrompt(prompt);
    }

    /**
     * Find a prompt by its id.
     *
     * @param id the prompt id
     * @return the prompt
     */
    @Transactional
    public Prompt findById(UUID id) {
        return promptRepository.findPromptBId(id)
                .orElseThrow(() -> PromptNotFoundException.withId(id));
    }

    /**
     * Update a prompt.
     *
     * @param id the prompt id
     * @param promptUpdate the prompt update command
     * @return the updated prompt
     */
    @Transactional
    public Prompt updatePrompt(final UUID id, final PromptUpdate promptUpdate) {
        final Prompt currentPrompt = findById(id);
        currentPrompt.updateSystemPrompt(promptUpdate.getSystemContent());
        currentPrompt.updateUserPrompt(promptUpdate.getUserContent());
        promptRepository.updatePrompt(currentPrompt);
        return currentPrompt;
    }

    /**
     * set a prompt as default.
     * @param id
     */
    @Transactional
    public void setDefaultPrompt(UUID id) {
        promptRepository.setDefaultPrompt(id);
    }

    /**
     * Get the default prompt for a dataset type and a message type.
     *
     * @param datasetType the dataset type
     * @param messageType the message type
     * @return the default prompt
     */
    @Transactional
    public Prompt getDefaultPrompt(final DatasetTypeEnum datasetType, final MessageTypeEnum messageType) {
        return promptRepository.getDefaultPrompt(datasetType, messageType)
                .orElseThrow(PromptNotFoundException::withDefaultPrompt);
    }

    /**
     * update the status of a prompt.
     * @param id the prompt id
     * @param status the new status
     */
    @Transactional
    public void updatePromptStatus(UUID id, PromptStatusEnum status) {
        promptRepository.updatePromptStatus(id, status);
    }

    /**
     * Find all prompts for a dataset type and a message type.
     *
     * @param datasetType the dataset type
     * @param messageType the message type
     * @return the list of prompts
     */
    @Transactional
    public List<Prompt> findAllPromptsByDatasetTypeAndMessageType(DatasetTypeEnum datasetType, MessageTypeEnum messageType) {
        return promptRepository.findAllByDatasetTypeAndMessageType(datasetType, messageType, PromptStatusEnum.VALIDATED);
    }

    /**
     * Find all prompts by status.
     * @param status the status
     * @return the list of prompts
     */
    @Transactional
    public List<Prompt> findAllPromptsByStatus(PromptStatusEnum status) {
        return promptRepository.findAllPromptsByStatus(status);
    }

    /**
     * Find all prompts for a dataset type and a message type.
     *
     * @param datasetType the dataset type
     * @param messageType the message type
     * @return the list of prompts
     */
    @Transactional
    public Integer findMaxVersionByDatasetTypeAndMessageType(DatasetTypeEnum datasetType, MessageTypeEnum messageType) {
        return promptRepository.findMaxVersionByDatasetTypeAndMessageType(datasetType, messageType);
    }

    /**
     * Find a prompt by its dataset type, message type and version.
     *
     * @param datasetType the dataset type
     * @param messageType the message type
     * @param version the version
     * @return the prompt
     */
    @Transactional
    public Optional<Prompt> findByDatasetTypeAndMessageTypeAndVersion(DatasetTypeEnum datasetType, MessageTypeEnum messageType, Integer version) {
        return promptRepository.findPromptByDatasetTypeAndMessageTypeAndVersion(datasetType, messageType, version);
    }
}
