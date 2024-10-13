package be.unamur.fpgen.repository;

import java.util.UUID;

public interface OngoingGenerationItemRepository {

    void deleteById(UUID id);
}
