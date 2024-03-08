package edu.kit.elst.course_conceptualization;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Id
    private final CourseVersion version;

    @Embedded
    private CourseInformation courseInformation;

    @Embedded
    private CourseSchedule courseSchedule;

    @Embedded
    private Prerequisite prerequisite;

    Course(CourseVersion version, CourseInformation courseInformation) {
        this.version = version;
        this.courseInformation = courseInformation;

        this.prerequisite = null;
    }
}
