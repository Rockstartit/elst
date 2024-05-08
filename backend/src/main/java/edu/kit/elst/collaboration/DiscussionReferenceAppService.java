package edu.kit.elst.collaboration;

import edu.kit.elst.core.shared.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
@AllArgsConstructor
public class DiscussionReferenceAppService {
    private final Discussions discussions;
    private final PageReferences pageReferences;
    private final MockupReferences mockupReferences;
    private final CourseReferences courseReferences;
    private final DiscussionReferences discussionReferences;

    public DiscussionReferenceId createReference(DiscussionId discussionId, MockupId mockupId) {
        Discussion discussion = discussions.getReferenceById(discussionId);

        DiscussionReference reference = new MockupReference(discussion, mockupId);

        discussionReferences.save(reference);

        return reference.id();
    }

    public DiscussionReferenceId createReference(DiscussionId discussionId, PageId pageId) {
        Discussion discussion = discussions.getReferenceById(discussionId);

        DiscussionReference reference = new PageReference(discussion, pageId);

        discussionReferences.save(reference);

        return reference.id();
    }

    public DiscussionReferenceId createReference(DiscussionId discussionId, CourseId courseId) {
        Discussion discussion = discussions.getReferenceById(discussionId);

        DiscussionReference reference = new CourseReference(discussion, courseId);

        discussionReferences.save(reference);

        return reference.id();
    }

    public void deleteReference(DiscussionReferenceId referenceId) {
        discussionReferences.deleteById(referenceId);
    }

    public Collection<DiscussionId> openDiscussions(MockupId mockupId) {
        return discussionReferences.findAllOpenDiscussions(mockupId);
    }

    public Collection<DiscussionId> openDiscussions(PageId pageId) {
        return discussionReferences.findAllOpenDiscussions(pageId);
    }

    public Collection<DiscussionId> openDiscussions(CourseId courseId) {
        return discussionReferences.findAllOpenDiscussions(courseId);
    }

    public ReferencesToDiscussion references(DiscussionId discussionId) {
        Collection<CourseReference> courseRefs = courseReferences.findAllByDiscussionId(discussionId);
        Collection<PageReference> pageRefs = pageReferences.findAllByDiscussionId(discussionId);
        Collection<MockupReference> mockupRefs = mockupReferences.findAllByDiscussionId(discussionId);

        return new ReferencesToDiscussion(courseRefs, pageRefs, mockupRefs);
    }
}
