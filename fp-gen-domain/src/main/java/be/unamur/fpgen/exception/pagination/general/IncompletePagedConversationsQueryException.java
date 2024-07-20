package be.unamur.fpgen.exception.pagination.general;

public class IncompletePagedConversationsQueryException extends RuntimeException{
    public IncompletePagedConversationsQueryException(String message) {
        super(message);
    }
}
