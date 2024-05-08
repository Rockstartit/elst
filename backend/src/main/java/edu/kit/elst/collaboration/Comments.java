package edu.kit.elst.collaboration;

import edu.kit.elst.core.shared.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface Comments extends JpaRepository<Comment, CommentId> {
    static CommentId nextIdentity() {
        return new CommentId(UUID.randomUUID());
    }

    @Query("select comment from Comment comment " +
            "where comment.discussion = :discussion " +
            "order by comment.createdAt asc")
    List<Comment> findAllByDiscussion(Discussion discussion);
}
