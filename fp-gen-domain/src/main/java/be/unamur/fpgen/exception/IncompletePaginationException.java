package be.unamur.fpgen.exception;

public class IncompletePaginationException extends RuntimeException {
    public IncompletePaginationException(String message) {
        super(message);
    }
}
