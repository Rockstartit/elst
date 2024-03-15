package edu.kit.elst.course_conceptualization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseUnits extends JpaRepository<CourseUnit, CourseUnitId> {
    static CourseUnitId nextIdentity() {
        return new CourseUnitId(UUID.randomUUID());
    }

    @Query("select courseUnit from CourseUnit courseUnit " +
            "left join fetch courseUnit.learningGoals " +
            "where courseUnit.course.version = :version")
    Collection<CourseUnit> findAllByCourseVersion(CourseVersion version);

    @Query("select courseUnit from CourseUnit courseUnit " +
            "left join fetch courseUnit.learningGoals " +
            "where courseUnit.id = :courseUnitId")
    Optional<CourseUnit> findWithLearningGoalsById(CourseUnitId courseUnitId);
}
