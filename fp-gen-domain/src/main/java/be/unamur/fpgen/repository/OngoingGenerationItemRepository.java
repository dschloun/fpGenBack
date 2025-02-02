package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItemStatus;

import java.util.List;
import java.util.UUID;

/**
 * Repository for ongoing generation items.
 */
public interface OngoingGenerationItemRepository {

    /**
     * Deletes all ongoing generation items with the given ids.
     * @param ids the ids of the items to delete
     */
    void deleteAllByIdIn(List<UUID> ids);

    /**
     * Updates the status of the ongoing generation item with the given id.
     * @param id the id of the item to update
     * @param status the new status of the item
     */
    void updateStatus(UUID id, OngoingGenerationItemStatus status);
}
