package edu.kit.elst.communication;

import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.core.shared.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
interface DiscussionReferences extends JpaRepository<DiscussionReference, DiscussionReferenceId> {
    static DiscussionReferenceId nextIdentity() {
        return new DiscussionReferenceId(UUID.randomUUID());
    }

    @Query("select reference.discussion.id from MockupReference reference " +
            "where reference.mockupId = :mockupId")
    Set<DiscussionId> findAllDiscussions(MockupId mockupId);

    @Query("select reference.discussion.id from PageReference reference " +
            "where reference.pageId = :pageId")
    Set<DiscussionId> findAllDiscussions(PageId pageId);

    @Query("select reference.discussion.id from BuildingBlockReference reference " +
            "where reference.buildingBlockId = :buildingBlockId")
    Set<DiscussionId> findAllDiscussions(BuildingBlockId buildingBlockId);
}
