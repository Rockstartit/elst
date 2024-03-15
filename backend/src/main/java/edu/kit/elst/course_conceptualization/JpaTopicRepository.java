package edu.kit.elst.course_conceptualization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
interface JpaTopicRepository extends JpaRepository<Topic, TopicId> {
    void deleteAllByCourseUnit(CourseUnit courseUnit);

    Collection<Topic> findAllByCourseUnit(CourseUnit courseUnitId);
}
