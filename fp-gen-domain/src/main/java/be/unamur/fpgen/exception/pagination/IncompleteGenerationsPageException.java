package be.unamur.fpgen.exception.pagination;

public class IncompleteGenerationsPageException extends RuntimeException{
    public IncompleteGenerationsPageException(String message) {
        super(message);
    }
}
