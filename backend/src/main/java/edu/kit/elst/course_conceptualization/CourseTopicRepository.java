package edu.kit.elst.course_conceptualization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class CourseTopicRepository implements CourseTopics {
    private final CourseUnits courseUnits;
    private final JpaTopicRepository topicRepository;
    private final JpaSubtopicRepository subtopicRepository;

    @Override
    public void save(Subtopic subtopic) {
        subtopicRepository.save(subtopic);
    }

    @Override
    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public Optional<Topic> findTopicById(TopicId topicId) {
        return topicRepository.findById(topicId);
    }

    @Override
    public Optional<Subtopic> findSubtopicById(TopicId subtopicId) {
        return subtopicRepository.findById(subtopicId);
    }

    @Override
    public void deleteAllByCourseUnitId(CourseUnitId courseUnitId) {
        CourseUnit courseUnit = courseUnits.getReferenceById(courseUnitId);

        subtopicRepository.deleteAllByCourseUnit(courseUnit);
        topicRepository.deleteAllByCourseUnit(courseUnit);
    }

    @Override
    public void deleteTopicById(TopicId topicId) {
        Topic topic = topicRepository.getReferenceById(topicId);

        subtopicRepository.deleteAllByTopic(topic);
        topicRepository.deleteById(topicId);
    }

    @Override
    public void deleteSubtopicById(TopicId subtopicId) {
        subtopicRepository.deleteById(subtopicId);
    }
}
