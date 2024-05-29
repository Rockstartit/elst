package edu.kit.elst.rest_api;

import edu.kit.elst.core.shared.LessonId;
import edu.kit.elst.lesson_planning.*;
import edu.kit.elst.lesson_planning.TeachingUnit;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class LessonController implements LessonApi {
    private final LessonAppService lessonAppService;
    private final TeachingUnitAppService teachingUnitAppService;

    @Override
    public ResponseEntity<UUID> createLesson(CreateLessonRequest body) {
        Topic topic = new Topic(body.getTopic());

        LessonId lessonId = lessonAppService.createLesson(topic);

        return ResponseEntity.ok(lessonId.value());
    }

    @Override
    public ResponseEntity<Void> deleteLesson(UUID lessonId) {
        LessonId aLessonId = new LessonId(lessonId);

        lessonAppService.deleteLesson(aLessonId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> editLesson(UUID lessonId, EditLessonRequest body) {
        LessonId aLessonId = new LessonId(lessonId);

        if (body.getTopic() != null) {
            Topic topic = new Topic(body.getTopic());

            lessonAppService.editTopic(aLessonId, topic);
        }

        if (body.getInstructionalParameters() != null) {
            InstructionalParameters instructionalParameters = body.getInstructionalParameters();

            Subject subject = new Subject(instructionalParameters.getSubject());
            TargetAudience targetAudience = new TargetAudience(instructionalParameters.getTargetAudience());
            Schedule schedule = new Schedule(instructionalParameters.getSchedule());

            lessonAppService.editInstructionalParameters(aLessonId, subject, targetAudience, schedule);
        }

        if (body.getPreparationFactors() != null) {
            PreparationFactors preparationFactors = body.getPreparationFactors();

            PriorKnowledge priorKnowledge = new PriorKnowledge(preparationFactors.getPriorKnowledge());
            LearningPrerequisites learningPrerequisites = new LearningPrerequisites(preparationFactors.getLearningPrerequisites());
            ThematicAreas thematicAreas = new ThematicAreas(preparationFactors.getThematicAreas());

            lessonAppService.editPreparationFactors(aLessonId, priorKnowledge, learningPrerequisites, thematicAreas);
        }

        if (body.getLicense() != null) {
            License license = new License(body.getLicense());

            lessonAppService.editLicence(aLessonId, license);
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<LessonOverview>> getAllLessons() {
        Collection<edu.kit.elst.lesson_planning.Lesson> lessons = lessonAppService.lessons();

        return ResponseEntity.ok(lessons.stream()
                .map(this::mapToLessonOverview)
                .toList());
    }

    @Override
    public ResponseEntity<Lesson> getLesson(UUID lessonId) {
        LessonId aLessonId = new LessonId(lessonId);

        edu.kit.elst.lesson_planning.Lesson lesson = lessonAppService.lesson(aLessonId)
                .orElseThrow(() -> new LessonNotFoundException(aLessonId));

        Collection<TeachingUnit> teachingUnits = teachingUnitAppService.teachingUnits(aLessonId);

        return ResponseEntity.ok(mapToLesson(lesson, teachingUnits));
    }

    private LessonOverview mapToLessonOverview(edu.kit.elst.lesson_planning.Lesson lesson) {
        LessonOverview dto = new LessonOverview();

        dto.setId(lesson.id().value());
        dto.setTopic(lesson.topic().value());

        return dto;
    }

    private Lesson mapToLesson(edu.kit.elst.lesson_planning.Lesson lesson, Collection<TeachingUnit> teachingUnits) {
        Lesson dto = new Lesson();

        dto.setId(lesson.id().value());
        dto.setTopic(lesson.topic().value());

        lesson.subject().map(Subject::value).ifPresent(dto::setSubject);
        lesson.targetAudience().map(TargetAudience::value).ifPresent(dto::setTargetAudience);
        lesson.priorKnowledge().map(PriorKnowledge::value).ifPresent(dto::setPriorKnowledge);
        lesson.learningPrerequisites().map(LearningPrerequisites::value).ifPresent(dto::setLearningPrerequisites);
        lesson.thematicAreas().map(ThematicAreas::value).ifPresent(dto::setThematicAreas);
        lesson.license().map(License::value).ifPresent(dto::setLicense);

        dto.setTeachingUnits(teachingUnits.stream()
                .map(this::mapToTeachingUnitOverview)
                .toList());

        return dto;
    }

    private TeachingUnitOverview mapToTeachingUnitOverview(TeachingUnit teachingUnit) {
        TeachingUnitOverview dto = new TeachingUnitOverview();

        dto.setId(teachingUnit.id().value());
        dto.setTopic(teachingUnit.topic().value());
        dto.setOrder(UtilMapper.mapToBigDecimal(teachingUnit.order()));

        return dto;
    }
}
