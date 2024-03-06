package edu.kit.elst.course_conceptualization;

public class CourseTopicNotFoundException extends RuntimeException {
    public CourseTopicNotFoundException(TopicId topicId) {
        super(String.format("topic %s does not exist", topicId));
    }
}
