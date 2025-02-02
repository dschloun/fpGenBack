package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when a generation is not found.
 */
public class GenerationNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_GENERATION_NOT_FOUND";
    private static String NOT_FOUND_BY_ID = "Could not find generation with id: %s";

    public GenerationNotFoundException(String code, String message) {
        super(code, message);
    }

    public static GenerationNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new GenerationNotFoundException(FPGEN_CODE, message);
    }
}
