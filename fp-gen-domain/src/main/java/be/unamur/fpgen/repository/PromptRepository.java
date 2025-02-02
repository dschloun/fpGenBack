package be.unamur.fpgen.repository;

import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.prompt.PromptStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for the prompt
 */
public interface PromptRepository {

    /**
     * Find a prompt by its id
     * @param id the id of the prompt
     * @return the prompt empty if not found
     */
    Optional<Prompt> findPromptBId(UUID id);

    /**
     * Find a prompt by its dataset type, message type and version
     * @param datasetType the dataset type
     * @param messageType the message type
     * @param version the version
     * @return the prompt empty if not found
     */
    Optional<Prompt> findPromptByDatasetTypeAndMessageTypeAndVersion(DatasetTypeEnum datasetType, MessageTypeEnum messageType, Integer version);

    /**
     * Update the status of a prompt
     * @param id the id of the prompt
     * @param status the new status
     */
    void updatePromptStatus(UUID id, PromptStatusEnum status);

    /**
     * Set the prompt as the default prompt
     * @param id the id of the prompt
     */
    void setDefaultPrompt(UUID id);

    /**
     * Get the default prompt for a given dataset type and a message type
     * @param datasetType the dataset type
     * @param messageType the message type
     * @return the prompt empty if not found
     */
    Optional<Prompt> getDefaultPrompt(DatasetTypeEnum datasetType, MessageTypeEnum messageType);

    /**
     * find all the prompts for a given dataset type and message type
     * @param datasetType the dataset type
     * @param messageType the message type
     * @param status the status of the prompts
     * @return the list of prompts
     */
    List<Prompt> findAllByDatasetTypeAndMessageType(DatasetTypeEnum datasetType, MessageTypeEnum messageType, PromptStatusEnum status);

    /**
     * find all the prompts for a given status
     * @param status the status of the prompts
     * @return the list of prompts
     */
    List<Prompt> findAllPromptsByStatus(PromptStatusEnum type);

    /**
     * Save a prompt
     * @param prompt the prompt to save
     * @return the saved prompt
     */
    Prompt savePrompt(Prompt prompt);

    /**
     * Update a prompt
     * @param prompt the prompt to update
     */
    void updatePrompt(Prompt prompt);

    /**
     * Find the maximum version for a given dataset type and message type
     * @param datasetType the dataset type
     * @param messageType the message type
     * @return the maximum version
     */
    Integer findMaxVersionByDatasetTypeAndMessageType(DatasetTypeEnum datasetType, MessageTypeEnum messageType);
}
