package be.unamur.fpgen.Exception;

import java.util.UUID;

public class AuthorNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_AUTHOR_NOT_FOUND";
    private static String NOT_FOUND_BY_ID = "Could not find author with id: %s";

    public AuthorNotFoundException(String code, String message) {
        super(code, message);
    }

    public static AuthorNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new AuthorNotFoundException(FPGEN_CODE, message);
    }
}
