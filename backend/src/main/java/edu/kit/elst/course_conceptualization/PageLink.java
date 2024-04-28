package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.PageId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "page_links")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PageLink {
    @Id
    private final UUID surrogateId;

    @AttributeOverride(name = "value", column = @Column(name = "page_id"))
    private final PageId pageId;

    @AttributeOverride(name = "value", column = @Column(name = "target_page_id"))
    private final PageId targetPageId;

    public PageLink(PageId pageId, PageId targetPageId) {
        Guards.notNull(pageId, "pageId");
        Guards.notNull(targetPageId, "targetPageId");

        this.surrogateId = UUID.randomUUID();
        this.pageId = pageId;
        this.targetPageId = targetPageId;
    }
}
