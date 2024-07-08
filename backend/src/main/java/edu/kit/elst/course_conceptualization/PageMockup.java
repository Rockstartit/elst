package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.FileId;
import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.core.shared.UserId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "mockups")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PageMockup {
    @EmbeddedId
    private final MockupId id;

    private final PageId pageId;
    private final FileId fileId;

    @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    private UserId createdBy;

    @Lob
    @Column(length = 256)
    private String description;

    public PageMockup(PageId pageId, FileId fileId, UserId createdBy) {
        Guards.notNull(fileId, "fileId");
        Guards.notNull(pageId, "pageId");
        Guards.notNull(createdBy, "createdBy");

        this.id = PageMockups.nextIdentity();
        this.pageId = pageId;
        this.fileId = fileId;
        this.createdBy = createdBy;
    }

    public void description(String description) {
        this.description = Objects.requireNonNullElse(description, "");
    }
}
