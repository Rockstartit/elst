package edu.kit.elst.course_conceptualization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CourseService {
    private final Courses courses;

    public CourseVersion createEmptyCourse(CourseInformation courseInformation) {
        CourseVersion courseVersion = courses.newCourseVersion();

        Course course = new Course(courseVersion, courseInformation);
        courses.save(course);

        return courseVersion;
    }

    public CourseVersion evolveCourse(CourseVersion previousVersion) {
        Course previousCourse = courses.findById(previousVersion)
                .orElseThrow(() -> new CourseNotFoundException(previousVersion));

        CourseVersion courseVersion = courses.incrementVersion(previousVersion);

        Course course = new Course(courseVersion, previousCourse.courseInformation());
        courses.save(course);

        return courseVersion;
    }

    public void editCourseInformation(CourseVersion version, CourseInformation courseInformation) {
        Course course = courses.findById(version)
                .orElseThrow(() -> new CourseNotFoundException(version));

        course.courseInformation(courseInformation);
    }

    public void editCourseSchedule(CourseVersion version, CourseSchedule courseSchedule) {
        Course course = courses.findById(version)
                .orElseThrow(() -> new CourseNotFoundException(version));

        course.courseSchedule(courseSchedule);
    }

    public void editCoursePrerequisite(CourseVersion version, Prerequisite prerequisite) {
        Course course = courses.findById(version)
                .orElseThrow(() -> new CourseNotFoundException(version));

        course.prerequisite(prerequisite);
    }
}
