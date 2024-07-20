package be.unamur.fpgen.exception.pagination;

public class IncompletePagedConversationsQueryException extends RuntimeException{
    public IncompletePagedConversationsQueryException(String message) {
        super(message);
    }
}
