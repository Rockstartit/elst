package edu.kit.elst.course_conceptualization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CourseService {
    private final Courses courses;
    private final CourseUnitService courseUnitService;

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

    public void deleteCourse(CourseVersion version) {
        courseUnitService.deleteAll(version);
        courses.deleteById(version);
    }

    public Collection<Course> courses() {
        return courses.findAll();
    }

    public Optional<Course> course(CourseVersion version) {
        return courses.findById(version);
    }
}
