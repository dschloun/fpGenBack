package be.unamur.fpgen.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Utility class for handling violations in domain object builder.
 */
public abstract class ViolationHandler {
    //validation methods
    protected List<String> cannotBeNull(Object o, String objectName) {
        List<String> errors = new ArrayList<>();
        try {
            requireNonNull(o);
        } catch (NullPointerException e) {
            errors.add(String.format("%s cannot be null", objectName));
        }
        return errors;
    }

    //convert violation list in a message for the exception
    protected String buildMessage(String startMessage, List<String> violations) {
        String message;
        message = violations.stream()
                .collect(Collectors.joining(", ", "{", "}"));
        return startMessage + ": " + message;
    }
}
