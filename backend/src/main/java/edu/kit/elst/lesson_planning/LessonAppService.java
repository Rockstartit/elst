package edu.kit.elst.lesson_planning;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class LessonAppService {
    private final Lessons lessons;
    private final TeachingUnitAppService teachingUnitAppService;

    public LessonId createLesson(Topic topic) {
        Lesson lesson = new Lesson(topic);

        lessons.save(lesson);

        return lesson.id();
    }

    public void editTopic(LessonId lessonId, Topic topic) {
        Lesson lesson = lesson(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        lesson.topic(topic);
    }

    public void editInstructionalParameters(LessonId lessonId, Subject subject,
                                  TargetAudience targetAudience, Schedule schedule) {
        Lesson lesson = lesson(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        lesson.subject(subject);
        lesson.targetAudience(targetAudience);
        lesson.schedule(schedule);
    }

    public void editPreparationFactors(LessonId lessonId, PriorKnowledge priorKnowledge, CurriculumAlignment curriculumAlignment,
                                       LearningPrerequisites learningPrerequisites, FrameworkConditions frameworkConditions) {
        Lesson lesson = lesson(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        lesson.priorKnowledge(priorKnowledge);
        lesson.curriculumAlignment(curriculumAlignment);
        lesson.learningPrerequisites(learningPrerequisites);
        lesson.frameworkConditions(frameworkConditions);
    }

    public void editEducationalComponents(LessonId lessonId, AcquiredCompetences acquiredCompetences,
                                          InstructionMethods instructionMethods, ThematicAreas thematicAreas) {
        Lesson lesson = lesson(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        lesson.acquiredCompetences(acquiredCompetences);
        lesson.instructionMethods(instructionMethods);
        lesson.thematicAreas(thematicAreas);
    }

    public void editLicence(LessonId lessonId, License license) {
        Lesson lesson = lesson(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        lesson.license(license);
    }

    public void deleteLesson(LessonId lessonId) {
        teachingUnitAppService.deleteTeachingUnits(lessonId);
        lessons.deleteById(lessonId);
    }

    public Collection<Lesson> lessons() {
        return lessons.findAll();
    }

    public Optional<Lesson> lesson(LessonId lessonId) {
        return lessons.findById(lessonId);
    }
}
