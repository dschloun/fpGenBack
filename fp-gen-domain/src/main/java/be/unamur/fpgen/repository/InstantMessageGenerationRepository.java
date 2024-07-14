package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.InstantMessageGeneration;

import java.util.UUID;

public interface InstantMessageGenerationRepository {

    InstantMessageGeneration saveInstantMessageGeneration(InstantMessageGeneration instantMessageGeneration);

    InstantMessageGeneration getInstantMessageGenerationById(UUID instantMessageGenerationId);

    void deleteInstantMessageGenerationById(UUID instantMessageGenerationId);
}
