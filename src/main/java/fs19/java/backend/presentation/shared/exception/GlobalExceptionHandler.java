package fs19.java.backend.presentation.shared.exception;

import fs19.java.backend.presentation.shared.response.ErrorItem;
import fs19.java.backend.presentation.shared.response.GlobalStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<GlobalStatus<Void>> handleCompanyNotFoundException(CompanyNotFoundException ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalStatus<Void> status = new GlobalStatus<>(HttpStatus.NOT_FOUND.value(), List.of(error));
        return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalStatus<Void>> handleGenericException(Exception ex) {
        ErrorItem error = new ErrorItem(ex.getMessage());
        GlobalStatus<Void> status = new GlobalStatus<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(error));
        return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
