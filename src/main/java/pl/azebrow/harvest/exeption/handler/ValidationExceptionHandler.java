package pl.azebrow.harvest.exeption.handler;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        var fieldErrorMap = e.getBindingResult().getAllErrors().stream()
                .filter(err -> err instanceof FieldError)
                .map(err -> (FieldError) err)
                .collect(Collectors.groupingBy(FieldError::getField));
        var errors = new HashMap<String, String>();
        fieldErrorMap.forEach((field, fieldErrorList) -> {
            var message = fieldErrorList.stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            errors.put(field, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleConstraintViolationException(
            ConstraintViolationException e
    ) {
        var errors = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(cv -> ((PathImpl) cv.getPropertyPath()).getLeafNode().getName(), ConstraintViolation::getMessage));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
