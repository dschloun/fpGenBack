package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when a dataset is not in the required validation status
 */
public class DatasetValidatedException extends ConflictException {
    private static final String FPGEN_CODE = "FP_GEN_DATASET_VALIDATED";
    private static final String DATASET_VALIDATED_WITH_ID = "Dataset with id: %s is not in the required validation status";

    public DatasetValidatedException(String code, String message) {
        super(code, message);
    }

    public static DatasetValidatedException withId(final UUID datasetId){
        final String message = String.format(DATASET_VALIDATED_WITH_ID, datasetId);
        return new DatasetValidatedException(FPGEN_CODE, message);
    }
}
