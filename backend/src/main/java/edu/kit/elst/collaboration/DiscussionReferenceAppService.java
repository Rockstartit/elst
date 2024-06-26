package edu.kit.elst.collaboration;

import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.core.shared.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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

    public DiscussionReferenceId createReference(DiscussionId discussionId, BuildingBlockId buildingBlockId) {
        Discussion discussion = discussions.getReferenceById(discussionId);

        DiscussionReference reference = new BuildingBlockReference(discussion, buildingBlockId);

        discussionReferences.save(reference);

        return reference.id();
    }

    public void deleteReference(DiscussionReferenceId referenceId) {
        discussionReferences.deleteById(referenceId);
    }

    public Collection<DiscussionId> discussions(MockupId mockupId) {
        return discussionReferences.findAllDiscussions(mockupId);
    }

    public Collection<DiscussionId> discussions(PageId pageId) {
        return discussionReferences.findAllDiscussions(pageId);
    }

    public Collection<DiscussionId> discussions(BuildingBlockId buildingBlockId) {
        return discussionReferences.findAllDiscussions(buildingBlockId);
    }

    public ReferencesToDiscussion references(DiscussionId discussionId) {
        Collection<BuildingBlockReference> courseRefs = courseReferences.findAllByDiscussionId(discussionId);
        Collection<PageReference> pageRefs = pageReferences.findAllByDiscussionId(discussionId);
        Collection<MockupReference> mockupRefs = mockupReferences.findAllByDiscussionId(discussionId);

        return new ReferencesToDiscussion(courseRefs, pageRefs, mockupRefs);
    }
}
