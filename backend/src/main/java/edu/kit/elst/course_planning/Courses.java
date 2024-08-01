package edu.kit.elst.course_planning;

import edu.kit.elst.core.shared.CourseId;
import edu.kit.elst.core.shared.LessonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
interface Courses extends JpaRepository<Course, CourseId> {
    static CourseId nextIdentity() {
        return new CourseId(UUID.randomUUID());
    }

    Collection<Course> findAllByLessonId(LessonId lessonId);
}
