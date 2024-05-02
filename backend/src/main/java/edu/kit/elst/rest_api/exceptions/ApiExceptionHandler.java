package edu.kit.elst.rest_api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends BaseApiExceptionHandler {
    public ApiExceptionHandler() {
        super("Generic Exception");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception ex) {
        log.error("Unhandled API exception", ex);

        return problemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
}
