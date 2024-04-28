package edu.kit.elst.course_conceptualization;

import edu.kit.elst.content_upload.FileId;
import edu.kit.elst.core.Guards;
import edu.kit.elst.users.UserId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "mockups")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Mockup {
    @EmbeddedId
    private final MockupId id;

    private final PageId pageId;
    private final FileId fileId;
    private UserId createdBy;
    private String description;

    public Mockup(PageId pageId, FileId fileId, UserId createdBy) {
        Guards.notNull(fileId, "fileId");
        Guards.notNull(pageId, "pageId");
        Guards.notNull(createdBy, "createdBy");

        this.id = Mockups.nextIdentity();
        this.pageId = pageId;
        this.fileId = fileId;
        this.createdBy = createdBy;
    }

    public void description(String description) {
        this.description = Objects.requireNonNullElse(description, "");
    }
}
