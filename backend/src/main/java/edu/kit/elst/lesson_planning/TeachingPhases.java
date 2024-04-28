package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.shared.TeachingPhaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
interface TeachingPhases extends JpaRepository<TeachingPhase, TeachingPhaseId> {
    static TeachingPhaseId nextIdentity() {
        return new TeachingPhaseId(UUID.randomUUID());
    }

    Collection<TeachingPhase> findAllByTeachingUnit(TeachingUnit teachingUnit);
}
