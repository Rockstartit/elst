package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.shared.TeachingPhaseId;
import edu.kit.elst.core.shared.TeachingUnitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
interface TeachingPhases extends JpaRepository<TeachingPhase, TeachingPhaseId> {
    static TeachingPhaseId nextIdentity() {
        return new TeachingPhaseId(UUID.randomUUID());
    }

    Collection<TeachingPhase> findAllByTeachingUnit(TeachingUnit teachingUnit);

    @Query("select teachingPhase from TeachingPhase teachingPhase " +
            "join fetch teachingPhase.teachingUnit " +
            "where teachingPhase.teachingUnit.id in :teachingUnitIds")
    Collection<TeachingPhase> findAllByTeachingUnitIdIn(Set<TeachingUnitId> teachingUnitIds);

    @Query("select max(teachingPhase.order) from TeachingPhase teachingPhase " +
            "where teachingPhase.teachingUnit = :teachingUnit " +
            "group by teachingPhase.teachingUnit.id ")
    Long maxOrderByTeachingUnit(TeachingUnit teachingUnit);
}
