package edu.kit.elst.collaboration;

import edu.kit.elst.course_conceptualization.CourseId;
import edu.kit.elst.course_conceptualization.MockupId;
import edu.kit.elst.course_conceptualization.PageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
interface DiscussionReferences extends JpaRepository<DiscussionReference, DiscussionReferenceId> {
    static DiscussionReferenceId nextIdentity() {
        return new DiscussionReferenceId(UUID.randomUUID());
    }

    @Query("select reference.discussion.id from MockupReference reference " +
            "where reference.mockupId = :mockupId " +
            "and reference.discussion.state = edu.kit.elst.collaboration.DiscussionState.OPEN")
    Set<DiscussionId> findAllOpenDiscussions(MockupId mockupId);

    @Query("select reference.discussion.id from PageReference reference " +
            "where reference.pageId = :pageId " +
            "and reference.discussion.state = edu.kit.elst.collaboration.DiscussionState.OPEN")
    Set<DiscussionId> findAllOpenDiscussions(PageId pageId);

    @Query("select reference.discussion.id from CourseReference reference " +
            "where reference.courseId = :courseId " +
            "and reference.discussion.state = edu.kit.elst.collaboration.DiscussionState.OPEN")
    Set<DiscussionId> findAllOpenDiscussions(CourseId courseId);
}
