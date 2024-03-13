package edu.kit.elst.course_conceptualization;

import java.util.Optional;
import java.util.UUID;

public interface CourseTopics {
    static TopicId nextTopicId() {
        return new TopicId(UUID.randomUUID());
    }

    static TopicId nextSubtopicId() {
        return new TopicId(UUID.randomUUID());
    }

    void deleteAllByCourseUnitId(CourseUnitId courseUnitId);

    void save(Topic topic);

    void deleteTopicById(TopicId topicId);

    void deleteSubtopicById(TopicId subtopicId);

    Optional<Topic> findTopicById(TopicId topicId);

    void save(Subtopic subtopic);

    Optional<Subtopic> findSubtopicById(TopicId subtopicId);
}
