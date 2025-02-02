package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when a dataset is not found.
 */
public class DatasetNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_DATASET_NOT_FOUND";
    private static final String NOT_FOUND_BY_ID = "Could not find dataset with id: %s";
    private static final String NOT_FOUND_BY_FUNCTION = "Could not find dataset function %s in project id: %s";

    public DatasetNotFoundException(String code, String message) {
        super(code, message);
    }

    public static DatasetNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new DatasetNotFoundException(FPGEN_CODE, message);
    }

    public static DatasetNotFoundException withFunction(final String function, final UUID projectId){
        final String message = String.format(NOT_FOUND_BY_FUNCTION, function, projectId);
        return new DatasetNotFoundException(FPGEN_CODE, message);
    }
}
