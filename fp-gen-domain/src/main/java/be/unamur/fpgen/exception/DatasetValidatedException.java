package be.unamur.fpgen.exception;

import java.util.UUID;

public class DatasetValidatedException extends ConflictException {
    private static final String FPGEN_CODE = "FP_GEN_DATASET_VALIDATED";
    private static final String DATASET_VALIDATED_WITH_ID = "Could not execute any operation on validated dataset with id: %s";

    public DatasetValidatedException(String code, String message) {
        super(code, message);
    }

    public static DatasetValidatedException withId(final UUID datasetId){
        final String message = String.format(DATASET_VALIDATED_WITH_ID, datasetId);
        return new DatasetValidatedException(FPGEN_CODE, message);
    }
}
