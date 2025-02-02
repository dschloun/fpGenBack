package be.unamur.fpgen.exception;

/**
 * Exception thrown when an author is not the original author of the resource
 */
public class AuthorUnauthorizedException extends ConflictException{
    private static final String FPGEN_CODE = "FP_GEN_AUTHOR_UNAUTHORIZED";
    private static String UNAUTHORIZED_AUTHOR_TEXT = "Author with email: %s is not the original author of the resource";

    public AuthorUnauthorizedException(String code, String message) {
        super(code, message);
    }

    public static AuthorUnauthorizedException withEmail(final String email){
        final String message = String.format(UNAUTHORIZED_AUTHOR_TEXT, email);
        return new AuthorUnauthorizedException(FPGEN_CODE, message);
    }
}
