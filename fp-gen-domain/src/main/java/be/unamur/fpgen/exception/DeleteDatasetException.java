package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when trying to delete a dataset that is part of a project and is the original version.
 */
public class DeleteDatasetException extends ConflictException {
    private static final String FPGEN_CODE = "FP_GEN_DELETE_DATASET";
    private static final String DELETE_DATASET_WITH_ID = "Dataset with id: %s cannot be deleted because part of a project and is original version";

    public DeleteDatasetException(String code, String message) {
        super(code, message);
    }

    public static DeleteDatasetException withId(final UUID datasetId){
        final String message = String.format(DELETE_DATASET_WITH_ID, datasetId);
        return new DeleteDatasetException(FPGEN_CODE, message);
    }
}
