package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.shared.TeachingPhaseId;
import edu.kit.elst.core.shared.TeachingUnitId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TeachingPhaseAppService {
    private final TeachingUnits teachingUnits;
    private final TeachingPhases teachingPhases;
    private final LearningMaterialAppService learningMaterialAppService;

    public TeachingPhaseId createTeachingPhase(TeachingUnitId teachingUnitId, Topic topic) {
        TeachingUnit teachingUnit = teachingUnits.getReferenceById(teachingUnitId);

        TeachingPhase teachingPhase = new TeachingPhase(teachingUnit, topic);
        teachingPhases.save(teachingPhase);

        return teachingPhase.id();
    }

    public void editTopic(TeachingPhaseId teachingPhaseId, Topic topic) {
        TeachingPhase teachingPhase = teachingPhase(teachingPhaseId)
                .orElseThrow(() -> new TeachingPhaseNotFoundException(teachingPhaseId));

        teachingPhase.topic(topic);
    }

    public void editTeachingPhase(TeachingPhaseId teachingPhaseId, Duration timeFrame, LearningCyclePhase phase) {
        TeachingPhase teachingPhase = teachingPhase(teachingPhaseId)
                .orElseThrow(() -> new TeachingPhaseNotFoundException(teachingPhaseId));

        teachingPhase.timeFrame(timeFrame);
        teachingPhase.phase(phase);
    }

    public void deleteTeachingPhase(TeachingPhaseId teachingPhaseId) {
        learningMaterialAppService.deleteLearningMaterials(teachingPhaseId);
        teachingPhases.deleteById(teachingPhaseId);
    }

    private Optional<TeachingPhase> teachingPhase(TeachingPhaseId teachingPhaseId) {
        return teachingPhases.findById(teachingPhaseId);
    }

    void deleteTeachingPhases(TeachingUnitId teachingUnitId) {
        TeachingUnit teachingUnit = teachingUnits.getReferenceById(teachingUnitId);

        Collection<TeachingPhase> toDelete = teachingPhases.findAllByTeachingUnit(teachingUnit);

        for (TeachingPhase teachingPhase : toDelete) {
            deleteTeachingPhase(teachingPhase.id());
        }
    }

    public Collection<TeachingPhase> teachingPhases(TeachingUnitId teachingUnitId) {
        TeachingUnit teachingUnit = teachingUnits.getReferenceById(teachingUnitId);

        return teachingPhases.findAllByTeachingUnit(teachingUnit);
    }
}
