package edu.kit.elst.course_conceptualization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseUnits extends CrudRepository<CourseUnit, CourseUnitId> {
    static CourseUnitId nextIdentity() {
        return new CourseUnitId(UUID.randomUUID());
    }
}
