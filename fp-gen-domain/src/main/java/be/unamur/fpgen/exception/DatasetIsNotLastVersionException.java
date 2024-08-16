package be.unamur.fpgen.exception;

import java.util.UUID;

public class DatasetIsNotLastVersionException extends ConflictException {
    private static final String FPGEN_CODE = "FP_GEN_DATASET_IS_NOT_LAST_VERSION";
    private static final String DATASET_NOT_LAST_VERSION_WITH_ID = "Could not create a new version because dataset with id: %s is not last version";

    public DatasetIsNotLastVersionException(String code, String message) {
        super(code, message);
    }

    public static DatasetIsNotLastVersionException withId(final UUID datasetId){
        final String message = String.format(DATASET_NOT_LAST_VERSION_WITH_ID, datasetId);
        return new DatasetIsNotLastVersionException(FPGEN_CODE, message);
    }
}
