package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItemStatus;

import java.util.List;
import java.util.UUID;

public interface OngoingGenerationItemRepository {

    void deleteAllByIdIn(List<UUID> ids);

    void updateStatus(UUID id, OngoingGenerationItemStatus status);
}
