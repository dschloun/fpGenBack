package be.unamur.fpgen.exception.pagination.general;

public class IncompleteQueryPageException extends RuntimeException{
    public IncompleteQueryPageException(String message) {
        super(message);
    }
}
