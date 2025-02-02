package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when a result is not found
 */
public class ResultNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_RESULT_NOT_FOUND";
    private static String NOT_FOUND_BY_ID = "Could not find result with id: %s";


    public ResultNotFoundException(String code, String message) {
        super(code, message);
    }

    public static ResultNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new ResultNotFoundException(FPGEN_CODE, message);
    }

}
