package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when a dataset is not in the required last version state
 */
public class DatasetLastVersionException extends ConflictException {
    private static final String FPGEN_CODE = "FP_GEN_DATASET_IS_NOT_IN_THE_REQUIRED_LAST_VERSION_STATE";
    private static final String DATASET_WITH_ID_NOT_LAST_VERSION_REQUIRED_STATE = "Dataset with id: %s is not in the required last version state";

    public DatasetLastVersionException(String code, String message) {
        super(code, message);
    }

    public static DatasetLastVersionException withId(final UUID datasetId){
        final String message = String.format(DATASET_WITH_ID_NOT_LAST_VERSION_REQUIRED_STATE, datasetId);
        return new DatasetLastVersionException(FPGEN_CODE, message);
    }
}
