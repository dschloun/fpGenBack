package be.unamur.fpgen.exception;

import java.util.UUID;

/**
 * Exception thrown when a notification is not found.
 */
public class NotificationNotFoundException extends NotFoundException{

    private static final String FPGEN_CODE = "FP_GEN_NOTIFICATION_NOT_FOUND";
    private static String NOT_FOUND_BY_ID = "Could not find notification with id: %s";


    public NotificationNotFoundException(String code, String message) {
        super(code, message);
    }

    public static NotificationNotFoundException withId(final UUID id){
        final String message = String.format(NOT_FOUND_BY_ID, id);
        return new NotificationNotFoundException(FPGEN_CODE, message);
    }

}
