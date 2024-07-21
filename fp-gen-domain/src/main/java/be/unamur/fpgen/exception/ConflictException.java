package be.unamur.fpgen.exception;

public class ConflictException extends RuntimeException implements BusinessException{

    private final String code;

    public ConflictException(String code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public String getCode() {
        return null;
    }
}
