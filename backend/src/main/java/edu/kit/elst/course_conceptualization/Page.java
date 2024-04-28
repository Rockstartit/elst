package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.PageId;
import edu.kit.elst.core.shared.TeachingPhaseId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pages")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Page {
    @EmbeddedId
    private final PageId id;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    private TeachingPhaseId teachingPhaseId;

    private String title;

    public Page(Course course, TeachingPhaseId teachingPhaseId, String title) {
        Guards.notNull(course, "course");
        Guards.notNull(teachingPhaseId, "teachingPhaseId");
        Guards.notEmptyBlankOrNull(title, "title");

        this.id = Pages.nextIdentity();
        this.course = course;
        this.teachingPhaseId = teachingPhaseId;
        this.title = title;
    }
}
