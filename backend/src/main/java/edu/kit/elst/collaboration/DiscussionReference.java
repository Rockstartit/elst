package edu.kit.elst.collaboration;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.DiscussionReferenceId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "discussion_references")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public abstract class DiscussionReference {
    @EmbeddedId
    private final DiscussionReferenceId id;

    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "discussion_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private final Discussion discussion;

    public DiscussionReference(Discussion discussion) {
        Guards.notNull(discussion, "discussion");

        this.id = DiscussionReferences.nextIdentity();
        this.discussion = discussion;
    }
}
