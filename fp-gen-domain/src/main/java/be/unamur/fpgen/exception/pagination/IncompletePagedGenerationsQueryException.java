package be.unamur.fpgen.exception.pagination;

public class IncompletePagedGenerationsQueryException extends RuntimeException{
    public IncompletePagedGenerationsQueryException(String message) {
        super(message);
    }
}
