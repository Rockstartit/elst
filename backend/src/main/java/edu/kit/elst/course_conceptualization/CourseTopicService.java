package edu.kit.elst.course_conceptualization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@AllArgsConstructor
public class CourseTopicService {
    private final CourseUnits courseUnits;
    private final CourseTopics courseTopics;

    public TopicId createTopic(CourseUnitId courseUnitId, String title) {
        CourseUnit courseUnit = courseUnits.getReferenceById(courseUnitId);

        Topic topic = new Topic(courseUnit, title);
        courseTopics.save(topic);

        return topic.topicId();
    }

    public void editTopic(TopicId topicId, LocalDate date, String description, String content) {
        Topic topic = courseTopics.findTopicById(topicId)
                .orElseThrow(() -> new CourseTopicNotFoundException(topicId));

        topic.date(date);
        topic.description(description);
        topic.content(content);
    }

    public void deleteTopic(TopicId topicId) {
        courseTopics.deleteTopicById(topicId);
    }

    public TopicId createSubtopic(TopicId topicId, String title) {
        Topic topic = courseTopics.findTopicById(topicId)
                .orElseThrow(() -> new CourseTopicNotFoundException(topicId));

        Subtopic subtopic = topic.addSubtopic(title);
        courseTopics.save(subtopic);

        return subtopic.subtopicId();
    }

    public void editSubtopic(TopicId subtopicId, String title, LocalDate date, String description, String content) {
        Subtopic subtopic = courseTopics.findSubtopicById(subtopicId)
                .orElseThrow(() -> new CourseTopicNotFoundException(subtopicId));

        subtopic.title(title);
        subtopic.date(date);
        subtopic.description(description);
        subtopic.content(content);
    }

    public void deleteSubtopic(TopicId subtopicId) {
        courseTopics.deleteSubtopicById(subtopicId);
    }
}
