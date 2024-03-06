package edu.kit.elst.course_conceptualization;

public class CourseUnitNotFoundException extends RuntimeException {
    public CourseUnitNotFoundException(CourseUnitId courseUnitId) {
        super(String.format("course unit %s does not exist", courseUnitId));
    }
}
