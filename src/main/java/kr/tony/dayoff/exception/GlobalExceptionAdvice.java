package kr.tony.dayoff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException ex) {
        return new ResponseEntity(new ErrorDto(
                ex.getExceptionStatus().getMessage(),
                ex.getExceptionStatus().getStatusCode()),
                HttpStatus.valueOf(ex.getExceptionStatus().getStatusCode()));
    }
}
