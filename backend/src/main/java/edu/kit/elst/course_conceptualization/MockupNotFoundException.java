package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.MockupId;

public class MockupNotFoundException extends RuntimeException {
    public MockupNotFoundException(MockupId mockupId) {
        super(String.format("mockup %s does not exist", mockupId));
    }
}
