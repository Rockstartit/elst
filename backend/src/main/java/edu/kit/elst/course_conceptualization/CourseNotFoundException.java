package edu.kit.elst.course_conceptualization;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(CourseId courseId) {
        super(String.format("course %s does not exist", courseId));
    }
}
