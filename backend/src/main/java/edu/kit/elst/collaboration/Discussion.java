package edu.kit.elst.collaboration;

import edu.kit.elst.core.Guards;
import edu.kit.elst.users.UserId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Table(name = "discussions")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Discussion {
    @EmbeddedId
    private final DiscussionId id;

    private final UserId createdBy;

    private String title;

    private DiscussionState state;

    private Instant resolvedAt;

    public Discussion(UserId createdBy, String title) {
        Guards.notNull(createdBy, "createdBy");

        this.id = Discussions.nextIdentity();
        this.createdBy = createdBy;
        this.state = DiscussionState.OPEN;

        title(title);
    }

    public void title(String title) {
        Guards.notEmptyBlankOrNull(title, "title");

        this.title = title;
    }

    public void resolve(Instant resolvedAt) {
        Guards.notNull(resolvedAt, "resolvedAt");

        this.resolvedAt = resolvedAt;
        this.state = DiscussionState.RESOLVED;
    }
}
