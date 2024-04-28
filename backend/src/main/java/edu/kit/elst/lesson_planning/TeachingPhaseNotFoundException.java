package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.shared.TeachingPhaseId;

public class TeachingPhaseNotFoundException extends RuntimeException {
    public TeachingPhaseNotFoundException(TeachingPhaseId id) {
        super(String.format("not found with id %s", id));
    }
}
