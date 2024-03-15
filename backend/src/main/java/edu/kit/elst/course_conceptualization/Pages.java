package edu.kit.elst.course_conceptualization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface Pages extends JpaRepository<Page, PageId> {
    static PageId nextIdentity() {
        return new PageId(UUID.randomUUID());
    }

    void deleteAllByCourseUnitId(CourseUnitId courseUnitId);

    Collection<Page> findAllByCourseUnit(CourseUnit courseUnit);
}
