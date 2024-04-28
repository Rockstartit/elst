package edu.kit.elst.collaboration;

public class DiscussionNotFoundException extends RuntimeException {
    public DiscussionNotFoundException(DiscussionId discussionId) {
        super(String.format("discussion %s does not exist", discussionId));
    }
}
