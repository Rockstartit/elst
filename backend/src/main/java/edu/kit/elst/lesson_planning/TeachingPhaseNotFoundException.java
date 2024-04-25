package edu.kit.elst.lesson_planning;

public class TeachingPhaseNotFoundException extends RuntimeException {
    public TeachingPhaseNotFoundException(TeachingPhaseId id) {
        super(String.format("not found with id %s", id));
    }
}
