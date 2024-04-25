package edu.kit.elst.rest_api;

import edu.kit.elst.lesson_planning.LearningMaterialAppService;
import edu.kit.elst.lesson_planning.LearningMaterialId;
import edu.kit.elst.lesson_planning.TeachingPhaseId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class LearningMaterialController implements LearningMaterialApi {
    private final LearningMaterialAppService learningMaterialAppService;

    @Override
    public ResponseEntity<Void> deleteLearningMaterial(UUID learningMaterialId) {
        LearningMaterialId aLearningMaterialId = new LearningMaterialId(learningMaterialId);

        learningMaterialAppService.deleteLearningMaterial(aLearningMaterialId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> editLearningMaterial(UUID learningMaterialId, EditLearningMaterialRequest body) {
        LearningMaterialId aLearningMaterialId = new LearningMaterialId(learningMaterialId);

        learningMaterialAppService.editLearningMaterial(aLearningMaterialId, body.getName());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UUID> uploadLearningMaterial(UUID teachingPhaseId, String name, MultipartFile fileName) {
        TeachingPhaseId aTeachingPhaseId = new TeachingPhaseId(teachingPhaseId);

        LearningMaterialId learningMaterialId = learningMaterialAppService.uploadLearningMaterial(aTeachingPhaseId, fileName, name);

        return ResponseEntity.ok(learningMaterialId.value());
    }
}
