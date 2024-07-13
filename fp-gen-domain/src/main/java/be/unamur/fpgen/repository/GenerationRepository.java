package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.Generation;

public interface GenerationRepository {

    Generation saveInstantMessageGeneration(Generation generation);
}
