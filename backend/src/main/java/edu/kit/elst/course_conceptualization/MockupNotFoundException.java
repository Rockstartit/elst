package edu.kit.elst.course_conceptualization;

public class MockupNotFoundException extends RuntimeException {
    public MockupNotFoundException(MockupId mockupId) {
        super(String.format("mockup %s does not exist", mockupId));
    }
}
