package edu.kit.elst.rest_api;

import edu.kit.elst.course_conceptualization.*;
import edu.kit.elst.course_conceptualization.CourseVersion;
import edu.kit.elst.course_conceptualization.StudyMaterials;
import edut.kit.elst.rest_api.*;
import edut.kit.elst.rest_api.CourseUnit;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CourseUnitController implements CourseUnitApi {
    private final CourseUnitService courseUnitService;
    private final CourseTopicService courseTopicService;

    @Override
    public ResponseEntity<UUID> createCourseUnit(UUID courseId, BigDecimal versionNumber) {
        CourseVersion version = new CourseVersion(courseId, versionNumber.longValue());

        CourseUnitId courseUnitId = courseUnitService.createCourseUnit(version);

        return ResponseEntity.ok(courseUnitId.value());
    }

    @Override
    public ResponseEntity<Void> editCourseUnit(UUID courseUnitId, EditCourseUnitRequest body) {
        CourseUnitId aCourseUnitId = new CourseUnitId(courseUnitId);
        Collection<LearningGoal> learningGoals = body.getLearningGoals().stream()
                .map(LearningGoal::new)
                .toList();
        StudyMaterials studyMaterials = mapToStudyMaterials(body.getStudyMaterials());

        courseUnitService.editCourseUnit(aCourseUnitId, body.getDescription(), learningGoals, studyMaterials);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteCourseUnit(UUID courseUnitId) {
        CourseUnitId aCourseUnitId = new CourseUnitId(courseUnitId);

        courseUnitService.deleteCourseUnit(aCourseUnitId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<CourseUnit>> getAllCourseUnits(UUID courseId, BigDecimal versionNumber) {
        CourseVersion version = new CourseVersion(courseId, versionNumber.longValue());

        Collection<edu.kit.elst.course_conceptualization.CourseUnit> courseUnits
                = courseUnitService.courseUnits(version);

        return ResponseEntity.ok(courseUnits.stream()
                .map(this::mapToCourseUnit)
                .toList());
    }

    @Override
    public ResponseEntity<CourseUnit> getCourseUnit(UUID courseUnitId) {
        CourseUnitId aCourseUnitId = new CourseUnitId(courseUnitId);

        edu.kit.elst.course_conceptualization.CourseUnit courseUnit
                = courseUnitService.courseUnit(aCourseUnitId)
                .orElseThrow(() -> new CourseUnitNotFoundException(aCourseUnitId));

        return ResponseEntity.ok(mapToCourseUnit(courseUnit));
    }

    @Override
    public ResponseEntity<UUID> createTopic(UUID courseUnitId, AddTopicRequest body) {
        CourseUnitId aCourseUnitId = new CourseUnitId(courseUnitId);

        TopicId topicId = courseTopicService.createTopic(aCourseUnitId, body.getTitle());

        return ResponseEntity.ok(topicId.value());
    }

    @Override
    public ResponseEntity<Void> editTopic(UUID topicId, EditTopicRequest body) {
        TopicId aTopicId = new TopicId(topicId);

        courseTopicService.editTopic(aTopicId, body.getDate(), body.getDescription(), body.getContent());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteTopic(UUID topicId) {
        TopicId aTopicId = new TopicId(topicId);

        courseTopicService.deleteTopic(aTopicId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UUID> createSubtopic(UUID topicId, AddSubtopicRequest body) {
        TopicId aTopicId = new TopicId(topicId);

        TopicId subtopicId = courseTopicService.createSubtopic(aTopicId, body.getTitle());

        return ResponseEntity.ok(subtopicId.value());
    }

    @Override
    public ResponseEntity<Void> editSubtopic(UUID subtopicId, EditSubtopicRequest body) {
        TopicId aSubtopicId = new TopicId(subtopicId);

        courseTopicService.editSubtopic(aSubtopicId, body.getTitle(),
                body.getDate(), body.getDescription(), body.getContent());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteSubtopic(UUID subtopicId) {
        TopicId aSubtopicId = new TopicId(subtopicId);

        courseTopicService.deleteSubtopic(aSubtopicId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<CourseTopic>> getTopics(UUID courseUnitId) {
        CourseUnitId aCourseUnitId = new CourseUnitId(courseUnitId);

        Collection<Topic> topics = courseTopicService.topics(aCourseUnitId);
        Map<TopicId, List<Subtopic>> subtopics = courseTopicService.subtopics(aCourseUnitId).stream()
                .collect(Collectors.groupingBy(Subtopic::topicId));

        return ResponseEntity.ok(topics.stream()
                .map(topic -> mapToTopic(topic, subtopics.getOrDefault(topic.topicId(), Collections.emptyList())))
                .toList());
    }

    private CourseTopic mapToTopic(Topic topic, List<Subtopic> subtopics) {
        CourseTopic dto = new CourseTopic();

        dto.setId(topic.topicId().value());
        dto.setTitle(topic.title());
        dto.setContent(topic.content());
        dto.setDate(topic.date());
        dto.setDescription(topic.description());
        dto.setSubtopics(subtopics.stream()
                .map(this::mapToSubtopic)
                .toList());

        return dto;
    }

    private CourseSubtopic mapToSubtopic(Subtopic subtopic) {
        CourseSubtopic dto = new CourseSubtopic();

        dto.setId(subtopic.topicId().value());
        dto.setTitle(subtopic.title());
        dto.setContent(subtopic.content());
        dto.setDate(subtopic.date());
        dto.setDescription(subtopic.description());

        return dto;
    }

    private CourseUnit mapToCourseUnit(edu.kit.elst.course_conceptualization.CourseUnit courseUnit) {
        CourseUnit dto = new CourseUnit();

        dto.setId(courseUnit.id().value());
        dto.setDescription(courseUnit.description());
        dto.setLearningGoals(courseUnit.learningGoals().stream()
                .map(LearningGoal::value)
                .toList());

        courseUnit.studyMaterials().ifPresent(studyMaterials -> {
            dto.setStudyMaterials(mapToStudyMaterials(studyMaterials));
        });

        return dto;
    }

    private edut.kit.elst.rest_api.StudyMaterials mapToStudyMaterials(StudyMaterials studyMaterials) {
        edut.kit.elst.rest_api.StudyMaterials dto = new edut.kit.elst.rest_api.StudyMaterials();

        dto.setBibliography(studyMaterials.bibliography());
        dto.setEBook(studyMaterials.eBook());
        dto.setEReading(studyMaterials.eReading());
        dto.setRelatedLinks(studyMaterials.relatedLinks());

        return dto;
    }

    private StudyMaterials mapToStudyMaterials(edut.kit.elst.rest_api.StudyMaterials studyMaterials) {
        return new StudyMaterials(
                studyMaterials.getEReading(),
                studyMaterials.getEBook(),
                studyMaterials.getBibliography(),
                studyMaterials.getRelatedLinks()
        );
    }
}
