package edu.kit.elst.lesson_planning;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface Lessons extends JpaRepository<Lesson, LessonId> {
    static LessonId nextIdentity() {
        return new LessonId(UUID.randomUUID());
    }
}
