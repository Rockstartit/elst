package edu.kit.elst.rest_api;

import edu.kit.elst.collaboration.Comment;
import edu.kit.elst.collaboration.ReferencesToDiscussion;
import edu.kit.elst.core.shared.CourseId;
import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.core.shared.UserId;
import edu.kit.elst.course_conceptualization.Mockup;
import edu.kit.elst.course_conceptualization.Page;
import edu.kit.elst.lesson_planning.Lesson;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiscussionMapper {
    public static DiscussionOverview mapToDiscussionOverview(edu.kit.elst.collaboration.Discussion discussion, edu.kit.elst.users.User createdBy) {
        DiscussionOverview dto = new DiscussionOverview();

        dto.setId(discussion.id().value());
        dto.setTitle(discussion.title());
        dto.setCreatedBy(mapToUser(createdBy));
        dto.setState(discussion.state());

        discussion.resolvedAt().ifPresent(resolvedAt -> dto.setResolvedAt(resolvedAt.atOffset(ZoneOffset.UTC)));

        return dto;
    }

    public static Discussion mapToDiscussion(edu.kit.elst.collaboration.Discussion discussion,
                                       edu.kit.elst.users.User createdBy,
                                       ReferencesToDiscussion references,
                                       Map<CourseId, edu.kit.elst.lesson_planning.Lesson> lessonMap,
                                       Map<PageId, edu.kit.elst.course_conceptualization.Page> pageMap,
                                       Map<MockupId, edu.kit.elst.course_conceptualization.Mockup> mockupMap) {
        Discussion dto = new Discussion();

        dto.setId(discussion.id().value());
        dto.setTitle(discussion.title());
        dto.setCreatedBy(mapToUser(createdBy));
        dto.setState(discussion.state());

        discussion.resolvedAt().ifPresent(resolvedAt -> dto.setResolvedAt(resolvedAt.atOffset(ZoneOffset.UTC)));

        List<DiscussionReference> referenceDTOs = new ArrayList<>();
        referenceDTOs.addAll(references.courseReferences().stream()
                .map(reference -> mapToCourseReference(reference, lessonMap.get(reference.courseId())))
                .toList());
        referenceDTOs.addAll(references.pageReferences().stream()
                .map(reference -> mapToPageReference(reference, pageMap.get(reference.pageId())))
                .toList());
        referenceDTOs.addAll(references.mockupReferences().stream()
                .map(reference -> mapToMockupReference(reference, mockupMap.get(reference.mockupId())))
                .toList());

        dto.setReferences(referenceDTOs);

        return dto;
    }

    private static edu.kit.elst.rest_api.MockupReference mapToMockupReference(edu.kit.elst.collaboration.MockupReference reference, Mockup mockup) {
        edu.kit.elst.rest_api.MockupReference dto = new edu.kit.elst.rest_api.MockupReference();

        dto.setMockupId(reference.mockupId().value());
        dto.setDescription(mockup.description());

        return dto;
    }

    private static edu.kit.elst.rest_api.PageReference mapToPageReference(edu.kit.elst.collaboration.PageReference reference, Page page) {
        edu.kit.elst.rest_api.PageReference dto = new edu.kit.elst.rest_api.PageReference();

        dto.setPageId(reference.pageId().value());
        dto.setTitle(page.title());

        return dto;
    }

    private static edu.kit.elst.rest_api.CourseReference mapToCourseReference(edu.kit.elst.collaboration.CourseReference reference, Lesson lesson) {
        edu.kit.elst.rest_api.CourseReference dto = new edu.kit.elst.rest_api.CourseReference();

        dto.setCourseId(reference.courseId().value());
        dto.setTopic(lesson.topic().value());

        return dto;
    }

    private static edu.kit.elst.rest_api.User mapToUser(edu.kit.elst.users.User user) {
        edu.kit.elst.rest_api.User dto = new edu.kit.elst.rest_api.User();

        dto.setId(user.id().value());
        dto.setFirstName(user.firstName());
        dto.setLastName(user.lastName());

        return dto;
    }

    public static edu.kit.elst.rest_api.Comment mapToComment(Comment comment, edu.kit.elst.users.User createdBy) {
        edu.kit.elst.rest_api.Comment dto = new edu.kit.elst.rest_api.Comment();

        dto.setId(comment.id().value());
        dto.setCreatedBy(mapToUser(createdBy));
        dto.setCreatedAt(comment.createdAt().atOffset(ZoneOffset.UTC));
        dto.setContent(comment.content());

        return dto;
    }
}
