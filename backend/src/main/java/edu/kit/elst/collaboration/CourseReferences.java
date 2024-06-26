package edu.kit.elst.collaboration;

import edu.kit.elst.core.shared.DiscussionId;
import edu.kit.elst.core.shared.DiscussionReferenceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CourseReferences extends JpaRepository<BuildingBlockReference, DiscussionReferenceId> {
    Collection<BuildingBlockReference> findAllByDiscussionId(DiscussionId discussionId);
}
