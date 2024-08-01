package edu.kit.elst.rest_api.exceptions;

import edu.kit.elst.building_blocks.BuildingBlockNotFoundException;
import edu.kit.elst.communication.CommentNotFoundException;
import edu.kit.elst.communication.DiscussionNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommunicationExceptionHandler extends BaseApiExceptionHandler {
    public CommunicationExceptionHandler() {
        super("Building Block Exception");
    }

    @ExceptionHandler(DiscussionNotFoundException.class)
    public ProblemDetail handle(DiscussionNotFoundException ex) {
        return problemDetail(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ProblemDetail handle(CommentNotFoundException ex) {
        return problemDetail(HttpStatus.NOT_FOUND, ex);
    }
}
