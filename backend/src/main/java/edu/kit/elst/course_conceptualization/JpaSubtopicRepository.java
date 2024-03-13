package edu.kit.elst.course_conceptualization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface JpaSubtopicRepository extends JpaRepository<Subtopic, TopicId> {
    void deleteAllByTopic(Topic topicId);

    @Query("delete from Subtopic subtopic " +
            "where subtopic.topic.courseUnit = :courseUnit")
    void deleteAllByCourseUnit(CourseUnit courseUnit);

}
