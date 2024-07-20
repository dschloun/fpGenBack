package be.unamur.fpgen.exception.pagination;

public class IncompletePagedInstantMessagesQueryException extends RuntimeException{
    public IncompletePagedInstantMessagesQueryException(String message) {
        super(message);
    }
}
