package edu.kit.elst.collaboration;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.CommentId;
import edu.kit.elst.core.shared.UserId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Comment {
    @EmbeddedId
    private final CommentId id;

    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "discussion_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private final Discussion discussion;

    private final UserId createdBy;

    private final Instant createdAt;

    @Lob
    @Column(length = 256)
    private String content;

    public Comment(Discussion discussion, UserId createdBy, Instant createdAt, String content) {
        Guards.notNull(discussion, "discussion");
        Guards.notNull(createdBy, "createdBy");
        Guards.notNull(createdAt, "createdAt");

        this.id = Comments.nextIdentity();
        this.discussion = discussion;
        this.createdBy = createdBy;
        this.createdAt = createdAt;

        content(content);
    }

    public void content(String content) {
        Guards.notEmptyBlankOrNull(content, "content");

        this.content = content;
    }
}
