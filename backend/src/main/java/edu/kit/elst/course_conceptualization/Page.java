package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
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
    private CourseUnit courseUnit;

    private String title;

    public Page(CourseUnit courseUnit, String title) {
        Guards.notNull(courseUnit, "courseUnit");
        Guards.notEmptyBlankOrNull(title, "title");

        this.id = Pages.nextIdentity();
        this.courseUnit = courseUnit;
        this.title = title;
    }
}
