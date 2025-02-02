package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when a project is not found.
 */
public class ProjectNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_PROJECT_NOT_FOUND";
    private static String NOT_FOUND_BY_ID = "Could not find project with id: %s";


    public ProjectNotFoundException(String code, String message) {
        super(code, message);
    }

    public static ProjectNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new ProjectNotFoundException(FPGEN_CODE, message);
    }

}
