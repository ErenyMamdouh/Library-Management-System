package com.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PatronException.class)
    public ResponseEntity<ApiException> handlePatronNotFoundException(PatronException ex){

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException apiException= new ApiException(
                ex.getMessage(),
                status
        );
        return new ResponseEntity<>(apiException, status);

    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<ApiException> handleBookNotFoundException(BookException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                status
        );
        return new ResponseEntity<>(apiException, status);
    }
        @ExceptionHandler(BorrowException.class)
        public ResponseEntity<ApiException> handleBorrowingRecordNotFoundException(BorrowException ex) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ApiException apiException= new ApiException(
                    ex.getMessage(),
                    status
            );
            return new ResponseEntity<>(apiException, status);
        }


}
