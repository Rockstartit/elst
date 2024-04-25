package edu.kit.elst.lesson_planning;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(LessonId id) {
        super(String.format("not found with id %s", id));
    }
}
