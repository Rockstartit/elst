package edu.kit.elst.collaboration;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.CourseId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CourseReference extends DiscussionReference {
    private final CourseId courseId;

    public CourseReference(Discussion discussion, CourseId courseId) {
        super(discussion);

        Guards.notNull(courseId, "courseId");
        this.courseId = courseId;
    }
}
