package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.CourseId;
import edu.kit.elst.core.shared.LessonId;
import edu.kit.elst.core.shared.UserId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "courses")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Course {
    @EmbeddedId
    private final CourseId id;

    private final LessonId lessonId;

    @Embedded
    private TechnologyWish technologyWish;

    private UserId owner;

    public Course(LessonId lessonId, TechnologyWish technologyWish, UserId owner) {
        Guards.notNull(lessonId, "lessonId");

        this.id = Courses.nextIdentity();
        this.lessonId = lessonId;

        technologyWish(technologyWish);
        owner(owner);
    }

    public void technologyWish(TechnologyWish technologyWish) {
        Guards.notNull(technologyWish, "technologyWish");

        this.technologyWish = technologyWish;
    }

    public void owner(UserId owner) {
        Guards.notNull(owner, "owner");

        this.owner = owner;
    }
}
