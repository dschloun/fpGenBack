package be.unamur.fpgen.exception.pagination;

public class IncompleteInstantMessagesPageException extends RuntimeException{
    public IncompleteInstantMessagesPageException(String message) {
        super(message);
    }
}
