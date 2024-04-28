package edu.kit.elst.collaboration;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(CommentId commentId) {
        super(String.format("comment %s does not exist", commentId));
    }
}
