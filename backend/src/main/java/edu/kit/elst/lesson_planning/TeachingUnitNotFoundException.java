package edu.kit.elst.lesson_planning;

public class TeachingUnitNotFoundException extends RuntimeException {
    public TeachingUnitNotFoundException(TeachingUnitId id) {
        super(String.format("not found with id %s", id));
    }
}
