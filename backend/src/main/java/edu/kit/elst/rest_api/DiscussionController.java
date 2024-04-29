package edu.kit.elst.rest_api;

import edu.kit.elst.collaboration.DiscussionAppService;
import edu.kit.elst.collaboration.DiscussionNotFoundException;
import edu.kit.elst.collaboration.DiscussionReferenceAppService;
import edu.kit.elst.core.shared.*;
import edu.kit.elst.users.User;
import edu.kit.elst.users.UserAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class DiscussionController implements DiscussionApi {
    private final DiscussionAppService discussionAppService;
    private final DiscussionReferenceAppService discussionReferenceAppService;
    private final UserAppService userAppService;

    @Override
    public ResponseEntity<UUID> startDiscussion(StartDiscussionRequest body) {
        DiscussionId discussionId = discussionAppService.startDiscussion(body.getTitle(), body.getComment());

        return ResponseEntity.ok(discussionId.value());
    }

    @Override
    public ResponseEntity<Void> editDiscussion(UUID discussionId, EditDiscussionRequest body) {
        DiscussionId aDiscussionId = new DiscussionId(discussionId);

        discussionAppService.editDiscussionTitle(aDiscussionId, body.getTitle());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> resolveDiscussion(UUID discussionId) {
        DiscussionId aDiscussionId = new DiscussionId(discussionId);

        discussionAppService.resolveDiscussion(aDiscussionId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Discussion>> getAllDiscussions(UUID courseId, UUID pageId, UUID mockupId) {
        Set<DiscussionId> discussionIds = new HashSet<>();

        if (courseId != null) {
            CourseId aCourseId = new CourseId(courseId);

            discussionIds.addAll(discussionReferenceAppService.openDiscussions(aCourseId));
        }

        if (pageId != null) {
            PageId aPageId = new PageId(pageId);

            discussionIds.addAll(discussionReferenceAppService.openDiscussions(aPageId));
        }

        if (mockupId != null) {
            MockupId aMockupId = new MockupId(mockupId);

            discussionIds.addAll(discussionReferenceAppService.openDiscussions(aMockupId));
        }

        Collection<edu.kit.elst.collaboration.Discussion> discussions = discussionAppService.discussions(discussionIds);
        Set<UserId> userIds = discussions.stream()
                .map(edu.kit.elst.collaboration.Discussion::createdBy)
                .collect(Collectors.toSet());

        Map<UserId, User> userMap = userAppService.users(userIds).stream()
                .collect(Collectors.toMap(User::id, Function.identity()));

        return ResponseEntity.ok(discussions.stream()
                .map(discussion -> mapToDiscussion(discussion, userMap.get(discussion.createdBy())))
                .toList());
    }

    @Override
    public ResponseEntity<Discussion> getDiscussion(UUID discussionId) {
        DiscussionId aDiscussionId = new DiscussionId(discussionId);

        edu.kit.elst.collaboration.Discussion discussion = discussionAppService.discussion(aDiscussionId)
                .orElseThrow(() -> new DiscussionNotFoundException(aDiscussionId));

        User createdBy = userAppService.user(discussion.createdBy());

        return ResponseEntity.ok(mapToDiscussion(discussion, createdBy));
    }

    @Override
    public ResponseEntity<UUID> addComment(UUID discussionId, AddCommentRequest body) {
        DiscussionId aDiscussionId = new DiscussionId(discussionId);

        CommentId commentId = discussionAppService.addComment(aDiscussionId, body.getContent());

        return ResponseEntity.ok(commentId.value());
    }

    @Override
    public ResponseEntity<Void> editComment(UUID commentId, EditCommentRequest body) {
        CommentId aCommentId = new CommentId(commentId);

        discussionAppService.editComment(aCommentId, body.getContent());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteComment(UUID commentId) {
        CommentId aCommentId = new CommentId(commentId);

        discussionAppService.deleteComment(aCommentId);

        return ResponseEntity.ok().build();
    }

    private Discussion mapToDiscussion(edu.kit.elst.collaboration.Discussion discussion, User createdBy) {
        Discussion dto = new Discussion();

        dto.setId(discussion.id().value());
        dto.setTitle(discussion.title());
        dto.setCreatedBy(mapToUser(createdBy));
        dto.setState(discussion.state());

        discussion.resolvedAt().ifPresent(resolvedAt -> dto.setResolvedAt(resolvedAt.atOffset(ZoneOffset.UTC)));

        return dto;
    }

    private edu.kit.elst.rest_api.User mapToUser(User user) {
        edu.kit.elst.rest_api.User dto = new edu.kit.elst.rest_api.User();

        dto.setId(user.id().value());
        dto.setFirstName(user.firstName());
        dto.setLastName(user.lastName());

        return dto;
    }
}
