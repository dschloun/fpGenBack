package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OngoingGenerationRepository {

    OngoingGeneration save(OngoingGeneration ongoingGeneration);

    Optional<OngoingGeneration> findById(UUID id);

    void addItemList(OngoingGeneration ongoingGeneration, List<OngoingGenerationItem> itemList);

    void updateStatus(UUID id, OngoingGenerationStatus status);

    List<OngoingGeneration> findAllByAuthorId(UUID authorId);

    List<OngoingGeneration> findAllByStatus(OngoingGenerationStatus status);

    void deleteById(UUID id);
}
