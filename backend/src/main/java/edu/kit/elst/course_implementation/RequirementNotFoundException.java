package edu.kit.elst.course_implementation;

public class RequirementNotFoundException extends RuntimeException {
    public RequirementNotFoundException(RequirementId requirementId) {
        super(String.format("requirement %s does not exist", requirementId));
    }
}
