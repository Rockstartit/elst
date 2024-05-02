package edu.kit.elst.rest_api.exceptions;

import edu.kit.elst.lesson_planning.LessonNotFoundException;
import edu.kit.elst.lesson_planning.TeachingPhaseNotFoundException;
import edu.kit.elst.lesson_planning.TeachingUnitNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LessonPlanningExceptionHandler extends BaseApiExceptionHandler {
    public LessonPlanningExceptionHandler() {
        super("Lesson Planning Exception");
    }

    @ExceptionHandler(LessonNotFoundException.class)
    public ProblemDetail handle(LessonNotFoundException ex) {
        return problemDetail(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(TeachingPhaseNotFoundException.class)
    public ProblemDetail handle(TeachingPhaseNotFoundException ex) {
        return problemDetail(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(TeachingUnitNotFoundException.class)
    public ProblemDetail handle(TeachingUnitNotFoundException ex) {
        return problemDetail(HttpStatus.NOT_FOUND, ex);
    }
}
