package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when an instant message is not found.
 */
public class InstantMessageNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_INSTANT_MESSAGE_NOT_FOUND";
    private static String NOT_FOUND_BY_ID = "Could not find instant message with id: %s";

    public InstantMessageNotFoundException(String code, String message) {
        super(code, message);
    }

    public static InstantMessageNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new InstantMessageNotFoundException(FPGEN_CODE, message);
    }
}
