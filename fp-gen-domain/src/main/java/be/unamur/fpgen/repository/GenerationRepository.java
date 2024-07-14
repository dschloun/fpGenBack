package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.AbstractGeneration;

public interface GenerationRepository {

    AbstractGeneration saveInstantMessageGeneration(AbstractGeneration abstractGeneration);
}
