package be.unamur.fpgen.exception.pagination.general;

public class IncompletePaginationException extends RuntimeException {
    public IncompletePaginationException(String message) {
        super(message);
    }
}
