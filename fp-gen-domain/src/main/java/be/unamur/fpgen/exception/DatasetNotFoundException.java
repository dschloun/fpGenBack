package be.unamur.fpgen.exception;

import java.util.UUID;

public class DatasetNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_DATASET_NOT_FOUND";
    private static final String NOT_FOUND_BY_ID = "Could not find dataset with id: %s";

    public DatasetNotFoundException(String code, String message) {
        super(code, message);
    }

    public static DatasetNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new DatasetNotFoundException(FPGEN_CODE, message);
    }
}
