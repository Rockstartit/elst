package edu.kit.elst.collaboration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface Discussions extends JpaRepository<Discussion, DiscussionId> {
    static DiscussionId nextIdentity() {
        return new DiscussionId(UUID.randomUUID());
    }
}
