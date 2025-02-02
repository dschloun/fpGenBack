package be.unamur.fpgen.exception;

/**
 * Exception thrown when an author already exists in the database
 */
public class AuthorAlreadyExistException extends ConflictException{
    private static final String FPGEN_CODE = "FP_GEN_AUTHOR_ALREADY_EXISTS";
    private static String ALREADY_EXIST_WITH_TRIGRAM = "Author with trigram: %s already exists";

    public AuthorAlreadyExistException(String code, String message) {
        super(code, message);
    }

    public static AuthorAlreadyExistException withTrigram(final String trigram){
        final String message = String.format(ALREADY_EXIST_WITH_TRIGRAM, trigram);
        return new AuthorAlreadyExistException(FPGEN_CODE, message);
    }
}
