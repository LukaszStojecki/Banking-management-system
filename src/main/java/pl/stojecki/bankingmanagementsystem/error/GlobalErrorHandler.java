package pl.stojecki.bankingmanagementsystem.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.stojecki.bankingmanagementsystem.exception.*;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Object> handleEntityExists(ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorDetails(e.getMessage()));
    }

    @ExceptionHandler(value = EmailException.class)
    public ResponseEntity<Object> handleEmailException(EmailException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDetails(e.getMessage()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDetails(e.getMessage()));
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handlePasswordValidation(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetails(e.getMessage()));
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    public ResponseEntity<Object> handleTokenRefreshException(TokenRefreshException e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ErrorDetails(e.getMessage()));
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<Object> handleTokenRefreshException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDetails(e.getMessage()));
    }
}
