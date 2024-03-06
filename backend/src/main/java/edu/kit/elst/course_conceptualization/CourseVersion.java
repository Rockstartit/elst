package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CourseVersion implements Serializable {
    @Column(name = "course_id", nullable = false)
    private final UUID courseId;

    @Column(name = "course_version_number", nullable = false)
    private final long versionNumber;

    CourseVersion(UUID courseId, long versionNumber) {
        Guards.notNull(courseId, "courseId");

        if (versionNumber <= 0) {
            throw new IllegalArgumentException("course version cannot be less than 1");
        }

        this.courseId = courseId;
        this.versionNumber = versionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseVersion version = (CourseVersion) o;
        return versionNumber == version.versionNumber && Objects.equals(courseId, version.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, versionNumber);
    }

    @Override
    public String toString() {
        return courseId.toString() + "#" + versionNumber;
    }
}
