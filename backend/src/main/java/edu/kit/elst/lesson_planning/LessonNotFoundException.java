package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.shared.LessonId;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(LessonId id) {
        super(String.format("not found with id %s", id));
    }
}
