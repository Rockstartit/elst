package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.shared.TeachingUnitId;

public class TeachingUnitNotFoundException extends RuntimeException {
    public TeachingUnitNotFoundException(TeachingUnitId id) {
        super(String.format("not found with id %s", id));
    }
}
