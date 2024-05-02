package edu.kit.elst.rest_api.exceptions;

import edu.kit.elst.core.Guards;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

public class BaseApiExceptionHandler extends ResponseEntityExceptionHandler {
    private final String title;

    public BaseApiExceptionHandler(String title) {
        Guards.notEmptyBlankOrNull(title, "title");

        this.title = title;
    }

    protected ProblemDetail problemDetail(HttpStatus status, Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle(title);
        problemDetail.setType(URI.create("https://elst.de/errors/" + ex.getClass().getSimpleName()));

        return problemDetail;
    }
}
