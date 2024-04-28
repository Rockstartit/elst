package edu.kit.elst.collaboration;

import edu.kit.elst.core.shared.DiscussionId;

public class DiscussionNotFoundException extends RuntimeException {
    public DiscussionNotFoundException(DiscussionId discussionId) {
        super(String.format("discussion %s does not exist", discussionId));
    }
}
