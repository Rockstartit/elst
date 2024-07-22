package edu.kit.elst.communication;

import edu.kit.elst.core.shared.CommentId;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(CommentId commentId) {
        super(String.format("comment %s does not exist", commentId));
    }
}
