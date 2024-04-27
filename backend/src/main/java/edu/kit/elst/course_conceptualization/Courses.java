package edu.kit.elst.course_conceptualization;

import edu.kit.elst.lesson_planning.LessonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
interface Courses extends JpaRepository<Course, CourseId> {
    static CourseId nextIdentity() {
        return new CourseId(UUID.randomUUID());
    }

    Collection<Course> findAllByLessonId(LessonId lessonId);
}
