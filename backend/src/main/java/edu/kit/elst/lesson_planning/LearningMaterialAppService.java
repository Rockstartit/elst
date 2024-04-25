package edu.kit.elst.lesson_planning;

import edu.kit.elst.content_upload.FileId;
import edu.kit.elst.content_upload.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LearningMaterialAppService {
    private final StorageService storageService;
    private final TeachingPhases teachingPhases;
    private final LearningMaterials learningMaterials;

    public LearningMaterialId uploadLearningMaterial(TeachingPhaseId teachingPhaseId, MultipartFile file, String name) {
        TeachingPhase teachingPhase = teachingPhases.getReferenceById(teachingPhaseId);
        FileId fileId = storageService.storeFile(file);

        LearningMaterial learningMaterial = new LearningMaterial(teachingPhase, fileId, name);

        learningMaterials.save(learningMaterial);

        return learningMaterial.id();
    }

    public void editLearningMaterial(LearningMaterialId learningMaterialId, String name) {
        Optional<LearningMaterial> learningMaterialOptional = learningMaterials.findById(learningMaterialId);

        learningMaterialOptional.ifPresent(learningMaterial -> {
            learningMaterial.name(name);
        });
    }

    public void deleteLearningMaterial(LearningMaterialId learningMaterialId) {
        Optional<LearningMaterial> learningMaterialOptional = learningMaterials.findById(learningMaterialId);

        learningMaterialOptional.ifPresent(learningMaterial -> {
            storageService.deleteFile(learningMaterial.fileId());
            learningMaterials.deleteById(learningMaterialId);
        });
    }

    void deleteLearningMaterials(TeachingPhaseId teachingPhaseId) {
        TeachingPhase teachingPhase = teachingPhases.getReferenceById(teachingPhaseId);

        Collection<LearningMaterial> toDelete = learningMaterials.findAllByTeachingPhase(teachingPhase);
        Set<FileId> fileIds = toDelete.stream().map(LearningMaterial::fileId).collect(Collectors.toSet());

        learningMaterials.deleteAll(toDelete);
        storageService.deleteFiles(fileIds);
    }

    public Collection<LearningMaterial> learningMaterials(Set<TeachingPhaseId> teachingPhaseIds) {
        return learningMaterials.findAllByTeachingPhaseIdIn(teachingPhaseIds);
    }
}
