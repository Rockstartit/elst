package edu.kit.elst.course_conceptualization;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

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

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "course")
    private Collection<CourseUnit> courseUnits;

    Course(CourseVersion version, CourseInformation courseInformation) {
        this.version = version;
        this.courseInformation = courseInformation;

        this.prerequisite = null;
        this.courseUnits = new ArrayList<>();
    }

    public CourseUnit addUnit() {
        return new CourseUnit(this);
    }
}
