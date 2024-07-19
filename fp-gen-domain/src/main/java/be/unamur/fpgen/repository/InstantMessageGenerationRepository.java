package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.InstantMessageGeneration;

import java.util.Optional;
import java.util.UUID;

public interface InstantMessageGenerationRepository {

    InstantMessageGeneration saveInstantMessageGeneration(InstantMessageGeneration instantMessageGeneration);

    Optional<InstantMessageGeneration> findInstantMessageGenerationById(UUID instantMessageGenerationId);

    void deleteInstantMessageGenerationById(UUID instantMessageGenerationId);
}
