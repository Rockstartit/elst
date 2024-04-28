package edu.kit.elst.rest_api;

import edu.kit.elst.core.shared.LessonId;
import edu.kit.elst.core.shared.TeachingPhaseId;
import edu.kit.elst.core.shared.TeachingUnitId;
import edu.kit.elst.lesson_planning.*;
import edu.kit.elst.lesson_planning.LearningMaterial;
import edu.kit.elst.lesson_planning.TeachingPhase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TeachingUnitController implements TeachingUnitApi {
    private final TeachingUnitAppService teachingUnitAppService;
    private final TeachingPhaseAppService teachingPhaseAppService;
    private final LearningMaterialAppService learningMaterialAppService;

    @Override
    public ResponseEntity<UUID> createTeachingUnit(UUID lessonId, CreateTeachingUnitRequest body) {
        LessonId aLessonId = new LessonId(lessonId);
        Topic topic = new Topic(body.getTopic());

        TeachingUnitId teachingUnitId = teachingUnitAppService.createTeachingUnit(aLessonId, topic);

        return ResponseEntity.ok(teachingUnitId.value());
    }

    @Override
    public ResponseEntity<Void> deleteTeachingUnit(UUID teachingUnitId) {
        TeachingUnitId aTeachingUnitId = new TeachingUnitId(teachingUnitId);

        teachingUnitAppService.deleteTeachingUnit(aTeachingUnitId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> editTeachingUnit(UUID teachingUnitId, EditTeachingUnitRequest body) {
        TeachingUnitId aTeachingUnitId = new TeachingUnitId(teachingUnitId);

        if (body.getTopic() != null) {
            Topic topic = new Topic(body.getTopic());

            teachingUnitAppService.editTopic(aTeachingUnitId, topic);
        }

        if (body.getAcquiredCompetences() != null) {
            AcquiredCompetences acquiredCompetences = new AcquiredCompetences(body.getAcquiredCompetences());

            teachingUnitAppService.editAcquiredCompetences(aTeachingUnitId, acquiredCompetences);
        }

        if (body.getDidacticConsiderations() != null) {
            DidacticConsideration didacticConsideration = new DidacticConsideration(body.getRoughContentAnalysis());

            teachingUnitAppService.editDidacticConsideration(aTeachingUnitId, didacticConsideration);
        }

        if (body.getRoughContentAnalysis() != null) {
            RoughContentAnalysis roughContentAnalysis = new RoughContentAnalysis(body.getRoughContentAnalysis());

            teachingUnitAppService.editRoughContentAnalysis(aTeachingUnitId, roughContentAnalysis);
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TeachingUnit> getTeachingUnit(UUID teachingUnitId) {
        TeachingUnitId aTeachingUnitId = new TeachingUnitId(teachingUnitId);

        edu.kit.elst.lesson_planning.TeachingUnit teachingUnit = teachingUnitAppService.teachingUnit(aTeachingUnitId)
                .orElseThrow(() -> new TeachingUnitNotFoundException(aTeachingUnitId));

        Collection<TeachingPhase> teachingPhases = teachingPhaseAppService.teachingPhases(aTeachingUnitId);

        Set<TeachingPhaseId> teachingPhaseIds = teachingPhases.stream().map(TeachingPhase::id).collect(Collectors.toSet());
        Map<TeachingPhaseId, List<LearningMaterial>> learningMaterials = learningMaterialAppService.learningMaterials(teachingPhaseIds).stream()
                .collect(Collectors.groupingBy(LearningMaterial::teachingPhaseId));

        return ResponseEntity.ok(mapToTeachingUnit(teachingUnit, teachingPhases, learningMaterials));
    }

    private TeachingUnit mapToTeachingUnit(edu.kit.elst.lesson_planning.TeachingUnit teachingUnit,
                                           Collection<TeachingPhase> teachingPhases,
                                           Map<TeachingPhaseId, List<LearningMaterial>> learningMaterials) {
        TeachingUnit dto = new TeachingUnit();

        dto.setId(teachingUnit.id().value());
        dto.setTopic(teachingUnit.topic().value());

        teachingUnit.didacticConsideration().map(DidacticConsideration::value).ifPresent(dto::setDidacticConsiderations);
        teachingUnit.roughContentAnalysis().map(RoughContentAnalysis::value).ifPresent(dto::setRoughContentAnalysis);
        teachingUnit.acquiredCompetences().map(AcquiredCompetences::value).ifPresent(dto::setAcquiredCompetences);

        dto.setTeachingPhases(teachingPhases.stream()
                .map(teachingPhase -> mapToTeachingPhase(teachingPhase, learningMaterials.getOrDefault(teachingPhase.id(), Collections.emptyList())))
                .toList());

        return dto;
    }

    private edu.kit.elst.rest_api.TeachingPhase mapToTeachingPhase(TeachingPhase teachingPhase, Collection<LearningMaterial> learningMaterials) {
        edu.kit.elst.rest_api.TeachingPhase dto = new edu.kit.elst.rest_api.TeachingPhase();

        dto.setId(teachingPhase.id().value());
        dto.setTopic(teachingPhase.topic().value());

        teachingPhase.timeFrame().map(UtilMapper::mapToBigDecimal).ifPresent(dto::setTimeFrame);
        teachingPhase.phase().ifPresent(dto::setPhase);

        dto.setLearningMaterials(learningMaterials.stream()
                .map(this::mapToLearningMaterial)
                .toList());

        return dto;
    }

    private edu.kit.elst.rest_api.LearningMaterial mapToLearningMaterial(LearningMaterial learningMaterial) {
        edu.kit.elst.rest_api.LearningMaterial dto = new edu.kit.elst.rest_api.LearningMaterial();

        dto.setId(learningMaterial.id().value());
        dto.setName(learningMaterial.name());
        dto.setFileId(learningMaterial.fileId().value());

        return dto;
    }
}
