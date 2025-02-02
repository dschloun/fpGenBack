package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when an author is not found in the database
 */
public class AuthorNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_AUTHOR_NOT_FOUND";
    private static String NOT_FOUND_BY_ID = "Could not find author with id: %s";
    private static String NOT_FOUND_BY_TRIGRAM = "Could not find author with trigram: %s";

    public AuthorNotFoundException(String code, String message) {
        super(code, message);
    }

    public static AuthorNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new AuthorNotFoundException(FPGEN_CODE, message);
    }

    public static AuthorNotFoundException withTrigram(final String trigram){
        final String message = String.format(NOT_FOUND_BY_TRIGRAM, trigram);
        return new AuthorNotFoundException(FPGEN_CODE, message);
    }
}
