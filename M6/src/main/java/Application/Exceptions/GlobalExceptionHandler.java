package Application.Exceptions;

import org.hibernate.ResourceClosedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;


@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<String> handleUnauthorizedAccess(Unauthorized e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handlerResponseEntity(ResourceClosedException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
