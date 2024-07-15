package be.unamur.fpgen.Exception;

public class NotFoundException extends RuntimeException implements BusinessException{
    private final String code;

    public NotFoundException(final String code, final String message) {
        super(message);
        this.code = code;
    }
    @Override
    public String getCode() {
        return this.code;
    }
}
