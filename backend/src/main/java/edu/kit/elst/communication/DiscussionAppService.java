package edu.kit.elst.communication;

import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.CommentId;
import edu.kit.elst.core.shared.DiscussionId;
import edu.kit.elst.core.shared.UserId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class DiscussionAppService {
    private final Clock clock;
    private final Comments comments;
    private final Discussions discussions;

    public DiscussionId startDiscussion(String title) {
        Discussion discussion = new Discussion(UserContext.getUserId(), title);

        discussions.save(discussion);

        return discussion.id();
    }

    public void editDiscussionTitle(DiscussionId discussionId, String title) {
        Discussion discussion = discussion(discussionId)
                .orElseThrow(() -> new DiscussionNotFoundException(discussionId));

        discussion.title(title);
    }

    public void resolveDiscussion(DiscussionId discussionId) {
        Discussion discussion = discussion(discussionId)
                .orElseThrow(() -> new DiscussionNotFoundException(discussionId));

        discussion.resolve(Instant.now(clock));
    }

    public Optional<Discussion> discussion(DiscussionId discussionId) {
        return discussions.findById(discussionId);
    }

    public CommentId addComment(DiscussionId discussionId, String content) {
        Discussion discussion = discussions.getReferenceById(discussionId);

        UserId createdBy = UserContext.getUserId();
        Instant createdAt =  Instant.now(clock);
        Comment comment = new Comment(discussion, createdBy, createdAt, content);

        comments.save(comment);

        return comment.id();
    }

    public void editComment(CommentId commentId, String content) {
        Comment comment = comment(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        comment.content(content);
    }

    public void deleteComment(CommentId commentId) {
        comments.deleteById(commentId);
    }

    public List<Comment> orderedComments(DiscussionId discussionId) {
        Discussion discussion = discussions.getReferenceById(discussionId);

        return comments.findAllByDiscussion(discussion);
    }

    private Optional<Comment> comment(CommentId commentId) {
        return comments.findById(commentId);
    }

    public Collection<Discussion> discussions(Set<DiscussionId> discussionIds) {
        return discussions.findAllById(discussionIds);
    }
}
