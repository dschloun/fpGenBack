package be.unamur.fpgen.exception;

/**
 * Interface for business exceptions.
 */
public interface BusinessException {

    String getCode();

    String getMessage();
}
