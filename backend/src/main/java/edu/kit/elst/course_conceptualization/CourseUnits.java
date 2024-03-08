package edu.kit.elst.course_conceptualization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseUnits extends JpaRepository<CourseUnit, CourseUnitId> {
    static CourseUnitId nextIdentity() {
        return new CourseUnitId(UUID.randomUUID());
    }
}
