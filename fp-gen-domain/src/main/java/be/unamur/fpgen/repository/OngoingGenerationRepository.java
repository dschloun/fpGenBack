package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for ongoing generation.
 */
public interface OngoingGenerationRepository {

    /**
     * Save the ongoing generation.
     * @param ongoingGeneration the ongoing generation to save
     * @return the saved ongoing generation
     */
    OngoingGeneration save(OngoingGeneration ongoingGeneration);

    /**
     * Find the ongoing generation by its id.
     * @param id the id of the ongoing generation
     * @return the ongoing generation empty if not found
     */
    Optional<OngoingGeneration> findById(UUID id);

    /**
     * Add items to the ongoing generation.
     * @param ongoingGeneration the ongoing generation
     * @param itemList the list of items to add
     */
    void addItemList(OngoingGeneration ongoingGeneration, List<OngoingGenerationItem> itemList);

    /**
     * Update the status of the ongoing generation.
     * @param id the id of the ongoing generation
     * @param status the new status
     */
    void updateStatus(UUID id, OngoingGenerationStatus status);

    /**
     * Find all ongoing generations by status.
     * @param status the status of the ongoing generation
     * @return the list of ongoing generations
     */
    List<OngoingGeneration> findAllByStatus(OngoingGenerationStatus status);

    /**
     * Delete the ongoing generation by its id.
     * @param id the id of the ongoing generation
     */
    void deleteById(UUID id);
}
