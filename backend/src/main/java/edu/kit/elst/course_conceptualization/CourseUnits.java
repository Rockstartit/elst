package edu.kit.elst.course_conceptualization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface CourseUnits extends JpaRepository<CourseUnit, CourseUnitId> {
    static CourseUnitId nextIdentity() {
        return new CourseUnitId(UUID.randomUUID());
    }

    @Query("select courseUnit from CourseUnit courseUnit " +
            "where courseUnit.course.version = :version")
    Collection<CourseUnit> findAllByCourseVersion(CourseVersion version);
}
