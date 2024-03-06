package edu.kit.elst.course_conceptualization;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(CourseVersion version) {
        super(String.format("course %s does not exist", version));
    }
}
