package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when a dataset is empty
 */
public class EmptyDatasetException extends ConflictException {
    private static final String FPGEN_CODE = "FP_GEN_DATASET_IS_EMPTY";
    private static final String DATASET_WITH_ID_IS_EMPTY = "Dataset with id: %s is empty";

    public EmptyDatasetException(String code, String message) {
        super(code, message);
    }

    public static EmptyDatasetException withId(final UUID datasetId){
        final String message = String.format(DATASET_WITH_ID_IS_EMPTY, datasetId);
        return new EmptyDatasetException(FPGEN_CODE, message);
    }
}
