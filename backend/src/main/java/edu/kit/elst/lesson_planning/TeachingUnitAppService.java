package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.shared.LessonId;
import edu.kit.elst.core.shared.TeachingUnitId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TeachingUnitAppService {
    private final Lessons lessons;
    private final TeachingUnits teachingUnits;
    private final TeachingPhaseAppService teachingPhaseAppService;

    public TeachingUnitId createTeachingUnit(LessonId lessonId, Topic topic) {
        Lesson lesson = lessons.getReferenceById(lessonId);

        TeachingUnit teachingUnit = new TeachingUnit(lesson, topic);

        teachingUnits.save(teachingUnit);

        return teachingUnit.id();
    }

    public void editTopic(TeachingUnitId teachingUnitId, Topic topic) {
        TeachingUnit teachingUnit = teachingUnit(teachingUnitId)
                .orElseThrow(() -> new TeachingUnitNotFoundException(teachingUnitId));

        teachingUnit.topic(topic);
    }

    public void editAcquiredCompetences(TeachingUnitId teachingUnitId, AcquiredCompetences acquiredCompetences) {
        TeachingUnit teachingUnit = teachingUnit(teachingUnitId)
                .orElseThrow(() -> new TeachingUnitNotFoundException(teachingUnitId));

        teachingUnit.acquiredCompetences(acquiredCompetences);
    }

    public void editRoughContentAnalysis(TeachingUnitId teachingUnitId, RoughContentAnalysis roughContentAnalysis) {
        TeachingUnit teachingUnit = teachingUnit(teachingUnitId)
                .orElseThrow(() -> new TeachingUnitNotFoundException(teachingUnitId));

        teachingUnit.roughContentAnalysis(roughContentAnalysis);
    }

    public void editDidacticConsideration(TeachingUnitId teachingUnitId, DidacticConsideration didacticConsideration) {
        TeachingUnit teachingUnit = teachingUnit(teachingUnitId)
                .orElseThrow(() -> new TeachingUnitNotFoundException(teachingUnitId));

        teachingUnit.didacticConsideration(didacticConsideration);
    }

    public void editCurriculumAlignment(TeachingUnitId teachingUnitId, CurriculumAlignment curriculumAlignment) {
        TeachingUnit teachingUnit = teachingUnit(teachingUnitId)
                .orElseThrow(() -> new TeachingUnitNotFoundException(teachingUnitId));

        teachingUnit.curriculumAlignment(curriculumAlignment);
    }

    public void editFrameworkConditions(TeachingUnitId teachingUnitId, FrameworkConditions frameworkConditions) {
        TeachingUnit teachingUnit = teachingUnit(teachingUnitId)
                .orElseThrow(() -> new TeachingUnitNotFoundException(teachingUnitId));

        teachingUnit.frameworkConditions(frameworkConditions);
    }

    public void editInstructionMethods(TeachingUnitId teachingUnitId, InstructionMethods instructionMethods) {
        TeachingUnit teachingUnit = teachingUnit(teachingUnitId)
                .orElseThrow(() -> new TeachingUnitNotFoundException(teachingUnitId));

        teachingUnit.instructionMethods(instructionMethods);
    }

    public void deleteTeachingUnit(TeachingUnitId teachingUnitId) {
        teachingPhaseAppService.deleteTeachingPhases(teachingUnitId);
        teachingUnits.deleteById(teachingUnitId);
    }

    public Optional<TeachingUnit> teachingUnit(TeachingUnitId teachingUnitId) {
        return teachingUnits.findById(teachingUnitId);
    }

    void deleteTeachingUnits(LessonId lessonId) {
        Lesson lesson = lessons.getReferenceById(lessonId);

        Collection<TeachingUnit> toDelete = teachingUnits.findAllByLesson(lesson);

        for (TeachingUnit teachingUnit : toDelete) {
            deleteTeachingUnit(teachingUnit.id());
        }
    }

    public Collection<TeachingUnit> teachingUnits(LessonId lessonId) {
        Lesson lesson = lessons.getReferenceById(lessonId);

        return teachingUnits.findAllByLesson(lesson);
    }
}
