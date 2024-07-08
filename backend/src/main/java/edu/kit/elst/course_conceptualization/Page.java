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
    @JoinColumn(name = "course_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    private TeachingPhaseId teachingPhaseId;

    private String title;

    @Lob
    @Column(length = 65536)
    private String notes;

    @Enumerated(EnumType.ORDINAL)
    private ImplementationStatus implementationStatus;

    @Column(name = "order_index")
    private long order;

    public Page(Course course, TeachingPhaseId teachingPhaseId, String title) {
        Guards.notNull(course, "course");
        Guards.notNull(teachingPhaseId, "teachingPhaseId");
        Guards.notEmptyBlankOrNull(title, "title");

        this.id = Pages.nextIdentity();
        this.course = course;
        this.teachingPhaseId = teachingPhaseId;
        this.title = title;
        this.notes = null;
        this.implementationStatus = ImplementationStatus.NOT_STARTED;
        this.order = 0;
    }

    public void implementationStatus(ImplementationStatus implementationStatus) {
        Guards.notNull(implementationStatus, "implementationStatus");

        this.implementationStatus = implementationStatus;
    }
}
