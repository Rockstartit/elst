package edu.kit.elst.rest_api;

import edu.kit.elst.course_conceptualization.*;
import edu.kit.elst.course_conceptualization.CourseVersion;
import edu.kit.elst.course_conceptualization.StudyMaterials;
import edut.kit.elst.rest_api.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

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

    private StudyMaterials mapToStudyMaterials(edut.kit.elst.rest_api.StudyMaterials studyMaterials) {
        return new StudyMaterials(
                studyMaterials.getEReading(),
                studyMaterials.getEBook(),
                studyMaterials.getBibliography(),
                studyMaterials.getRelatedLinks()
        );
    }
}
