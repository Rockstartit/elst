package edu.kit.elst.lesson_planning;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
 interface LearningMaterials extends JpaRepository<LearningMaterial, LearningMaterialId> {
    static LearningMaterialId nextIdentity() {
        return new LearningMaterialId(UUID.randomUUID());
    }

    Collection<LearningMaterial> findAllByTeachingPhase(TeachingPhase teachingPhase);

    @Query("select learningMaterial from LearningMaterial learningMaterial " +
            "join fetch learningMaterial.teachingPhase " +
            "where learningMaterial.teachingPhase.id in :teachingPhaseIds")
    Collection<LearningMaterial> findAllByTeachingPhaseIdIn(Set<TeachingPhaseId> teachingPhaseIds);
}
