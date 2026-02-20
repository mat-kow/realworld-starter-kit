package pl.teo.realworldapp.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiNotFoundException.class)
    public ResponseEntity<Object> handleApiNotFoundException(ApiNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.resolve(422);
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(ApiNonUniqueValueException.class)
    public ResponseEntity<Object> handleApiNonUniqueValueException(ApiNonUniqueValueException e) {
        HttpStatus status = HttpStatus.resolve(409);
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(ApiUnAuthorizedException.class)
    public ResponseEntity<Object> handleApiUnAuthorizedException(ApiUnAuthorizedException e) {
        HttpStatus status = HttpStatus.resolve(401);
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(ApiForbiddenException.class)
    public ResponseEntity<Object> handleApiForbiddenException(ApiForbiddenException e) {
        HttpStatus status = HttpStatus.resolve(403);
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, status);
    }
}
