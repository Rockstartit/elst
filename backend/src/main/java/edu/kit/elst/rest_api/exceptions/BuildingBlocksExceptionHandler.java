package edu.kit.elst.rest_api.exceptions;

import edu.kit.elst.building_blocks.BuildingBlockNotFoundException;
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
public class BuildingBlocksExceptionHandler extends BaseApiExceptionHandler {
    public BuildingBlocksExceptionHandler() {
        super("Building Block Exception");
    }

    @ExceptionHandler(BuildingBlockNotFoundException.class)
    public ProblemDetail handle(BuildingBlockNotFoundException ex) {
        return problemDetail(HttpStatus.NOT_FOUND, ex);
    }
}
