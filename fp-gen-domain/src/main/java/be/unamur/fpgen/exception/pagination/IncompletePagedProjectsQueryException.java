package be.unamur.fpgen.exception.pagination;

public class IncompletePagedProjectsQueryException extends RuntimeException{
    public IncompletePagedProjectsQueryException(String message) {
        super(message);
    }
}
