package be.unamur.fpgen.web;

import be.unamur.fpgen.exception.NotFoundException;
import be.unamur.model.ErrorResponse;
import be.unamur.model.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

/**
 * This class is used to handle exceptions thrown by the application in order to send exception back to the front end in a certain format.
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvise extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerAdvise.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception e, WebRequest request) {
        ErrorResponse error = getTechnicalErrorResponse(e);
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException(NotFoundException e, WebRequest request) {
        ErrorResponse error = getTechnicalErrorResponse(e);
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getTechnicalErrorResponse(Exception e) {
        UUID errorId = UUID.randomUUID();
        LOG.error("Error Id ({})", errorId, e);
        return new ErrorResponse()
                .technicalErrorInstanceId(errorId)
                .errorType(ErrorType.TECHNICAL);
    }
}
