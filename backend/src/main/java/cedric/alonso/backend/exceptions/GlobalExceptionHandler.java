package cedric.alonso.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * Global exception handler for handling application-wide exceptions.
 * Provides consistent JSON error responses for exceptions of type {@link BaseException}.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions of type {@link BaseException}.
     *
     * @param exception the exception being handled, which extends {@link BaseException}.
     * @return a {@link ResponseEntity} containing the error details as a JSON object,
     * including the message, HTTP status code, and reason phrase.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Map<String, Object>> handleBaseException(BaseException exception) {
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus status = (responseStatus != null) ?
                responseStatus.value() :
                HttpStatus.INTERNAL_SERVER_ERROR;

        Map<String, Object> errorResponse = Map.of(
                "message", exception.getMessage(),
                "status", status
        );

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}