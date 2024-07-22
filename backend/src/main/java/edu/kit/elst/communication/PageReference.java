package edu.kit.elst.communication;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.PageId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PageReference extends DiscussionReference {
    private final PageId pageId;

    public PageReference(Discussion discussion, PageId pageId) {
        super(discussion);

        Guards.notNull(pageId, "pageId");
        this.pageId = pageId;
    }
}
