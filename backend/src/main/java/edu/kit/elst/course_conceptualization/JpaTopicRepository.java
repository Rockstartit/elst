package edu.kit.elst.course_conceptualization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaTopicRepository extends JpaRepository<Topic, TopicId> {
    void deleteAllByCourseUnit(CourseUnit courseUnit);
}
