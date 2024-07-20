package be.unamur.fpgen.exception.pagination;

public class IncompleteConversationsPageException extends RuntimeException{
    public IncompleteConversationsPageException(String message) {
        super(message);
    }
}
