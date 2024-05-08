package edu.kit.elst.rest_api;

import edu.kit.elst.collaboration.CourseReference;
import edu.kit.elst.collaboration.MockupReference;
import edu.kit.elst.collaboration.PageReference;
import edu.kit.elst.collaboration.*;
import edu.kit.elst.core.shared.*;
import edu.kit.elst.course_conceptualization.Course;
import edu.kit.elst.course_conceptualization.Mockup;
import edu.kit.elst.course_conceptualization.Page;
import edu.kit.elst.course_conceptualization.*;
import edu.kit.elst.lesson_planning.Lesson;
import edu.kit.elst.lesson_planning.LessonAppService;
import edu.kit.elst.users.User;
import edu.kit.elst.users.UserAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class DiscussionController implements DiscussionApi {
    private final UserAppService userAppService;
    private final PageAppService pageAppService;
    private final CourseAppService courseAppService;
    private final LessonAppService lessonAppService;
    private final MockupAppService mockupAppService;
    private final DiscussionAppService discussionAppService;
    private final DiscussionReferenceAppService discussionReferenceAppService;

    @Override
    public ResponseEntity<UUID> startDiscussion(StartDiscussionRequest body) {
        DiscussionId discussionId = discussionAppService.startDiscussion(body.getTitle());

        if (body.getCourseId() != null) {
            CourseId courseId = new CourseId(body.getCourseId());

            discussionReferenceAppService.createReference(discussionId, courseId);
        }

        if (body.getPageId() != null) {
            PageId pageId = new PageId(body.getPageId());

            discussionReferenceAppService.createReference(discussionId, pageId);
        }

        if (body.getMockupId() != null) {
            MockupId mockupId = new MockupId(body.getMockupId());

            discussionReferenceAppService.createReference(discussionId, mockupId);
        }

        if (StringUtils.hasText(body.getComment())) {
            discussionAppService.addComment(discussionId, body.getComment());
        }

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
    public ResponseEntity<List<DiscussionOverview>> getAllDiscussions(UUID courseId, UUID pageId, UUID mockupId) {
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
                .map(discussion -> DiscussionMapper.mapToDiscussionOverview(discussion, userMap.get(discussion.createdBy())))
                .toList());
    }

    @Override
    public ResponseEntity<Discussion> getDiscussion(UUID discussionId) {
        DiscussionId aDiscussionId = new DiscussionId(discussionId);

        edu.kit.elst.collaboration.Discussion discussion = discussionAppService.discussion(aDiscussionId)
                .orElseThrow(() -> new DiscussionNotFoundException(aDiscussionId));

        User createdBy = userAppService.user(discussion.createdBy());

        ReferencesToDiscussion references = discussionReferenceAppService.references(aDiscussionId);

        Map<CourseId, Lesson> courseToLessonMap = getCourseToLessonMap(references.courseReferences());
        Map<PageId, Page> pageMap = getPageMap(references.pageReferences());
        Map<MockupId, Mockup> mockupMap = getMockupMap(references.mockupReferences());

        return ResponseEntity.ok(DiscussionMapper.mapToDiscussion(
                discussion,
                createdBy,
                references,
                courseToLessonMap,
                pageMap,
                mockupMap
        ));
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

    @Override
    public ResponseEntity<List<Comment>> getAllComments(UUID discussionId) {
        DiscussionId aDiscussionId = new DiscussionId(discussionId);

        List<edu.kit.elst.collaboration.Comment> comments = discussionAppService.orderedComments(aDiscussionId);

        Set<UserId> userIds = comments.stream()
                .map(edu.kit.elst.collaboration.Comment::createdBy)
                .collect(Collectors.toSet());

        Map<UserId, User> userMap = userAppService.users(userIds).stream()
                .collect(Collectors.toMap(User::id, Function.identity()));

        return ResponseEntity.ok(comments.stream()
                .map(comment -> DiscussionMapper.mapToComment(comment, userMap.get(comment.createdBy())))
                .toList());
    }

    private Map<MockupId, Mockup> getMockupMap(Collection<MockupReference> mockupReferences) {
        Set<MockupId> mockupIds = mockupReferences.stream()
                .map(MockupReference::mockupId)
                .collect(Collectors.toSet());

        return mockupAppService.mockups(mockupIds).stream()
                .collect(Collectors.toMap(Mockup::id, Function.identity()));
    }

    private Map<PageId, Page> getPageMap(Collection<PageReference> pageReferences) {
        Set<PageId> pageIds = pageReferences.stream()
                .map(PageReference::pageId)
                .collect(Collectors.toSet());

        return pageAppService.pages(pageIds).stream()
                .collect(Collectors.toMap(Page::id, Function.identity()));
    }

    private Map<CourseId, Lesson> getCourseToLessonMap(Collection<CourseReference> courseReferences) {
        Set<CourseId> courseIds = courseReferences.stream()
                .map(CourseReference::courseId)
                .collect(Collectors.toSet());

        Collection<Course> courses = courseAppService.courses(courseIds);

        Set<LessonId> lessonIds = courses.stream()
                .map(Course::lessonId)
                .collect(Collectors.toSet());

        Map<LessonId, Lesson> lessonMap = lessonAppService.lessons(lessonIds).stream()
                .collect(Collectors.toMap(Lesson::id, Function.identity()));

        return courses.stream()
                .collect(Collectors.toMap(Course::id, course -> lessonMap.get(course.lessonId())));
    }
}
