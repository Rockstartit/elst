package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.shared.TeachingUnitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
interface TeachingUnits extends JpaRepository<TeachingUnit, TeachingUnitId> {
    static TeachingUnitId nextIdentity() {
        return new TeachingUnitId(UUID.randomUUID());
    }

    Collection<TeachingUnit> findAllByLesson(Lesson lesson);

    @Query("select max(teachingUnit.order) from TeachingUnit teachingUnit " +
            "where teachingUnit.lesson = :lesson " +
            "group by teachingUnit.lesson.id ")
    Long maxOrderByLesson(Lesson lesson);
}
