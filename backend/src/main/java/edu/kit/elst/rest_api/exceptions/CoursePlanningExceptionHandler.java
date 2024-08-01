package edu.kit.elst.rest_api.exceptions;

import edu.kit.elst.course_planning.CourseNotFoundException;
import edu.kit.elst.course_planning.PageNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CoursePlanningExceptionHandler extends BaseApiExceptionHandler {
    public CoursePlanningExceptionHandler() {
        super("Course Planning Exception");
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ProblemDetail handle(CourseNotFoundException ex) {
        return problemDetail(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(PageNotFoundException.class)
    public ProblemDetail handle(PageNotFoundException ex) {
        return problemDetail(HttpStatus.NOT_FOUND, ex);
    }
}
