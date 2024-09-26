package sn.ashia.projekt.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Hidden
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                String fieldame = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldame, errorMessage);
            }
            else {
                errors.put("error", error.getDefaultMessage());
            }
        });
        errors.put("status", status.value());
        errors.put("timestamp", LocalDateTime.now());
        return handleExceptionInternal(ex, errors, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        String error = "invalid input";

        if (mostSpecificCause != null && mostSpecificCause.getMessage() != null) {
            String message = mostSpecificCause.getMessage();
            if (message.contains("Cannot deserialize value of type")) {
                error = "invalid value for enum: " + extractEnumFromMessage(message);
            }
        }

        Map<String, Object> errors = new HashMap<>();
        errors.put("error", error);
        errors.put("status", status.value());
        errors.put("timestamp", LocalDateTime.now());

        return handleExceptionInternal(ex, errors, headers, status, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = String.format("the parameter '%s' with value '%s' could not be converted to type '%s'",
                ex.getName(),
                ex.getValue(),
                Objects.requireNonNull(ex.getRequiredType()).getSimpleName()
        );

        Map<String, Object> errors = new HashMap<>();
        errors.put("error", error);
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("timestamp", LocalDateTime.now());

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleApi(ApiException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflict(ConflictException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();

        Map<String, Object> errors = new HashMap<>();
        errors.put("error", message);
        errors.put("timestamp", LocalDateTime.now());
        errors.put("status", status.value());

        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    private String extractEnumFromMessage(String message) {
        // Extract the enum type and valid values from the error message
        int enumTypeStart = message.indexOf("`") + 1;
        int enumTypeEnd = message.indexOf("`", enumTypeStart);
        String enumType = message.substring(enumTypeStart, enumTypeEnd);

        int valuesStart = message.indexOf("[") + 1;
        int valuesEnd = message.indexOf("]", valuesStart);
        String validValues = message.substring(valuesStart, valuesEnd);

        return "expected one of: " + validValues + " for enum " + enumType;
    }
}
