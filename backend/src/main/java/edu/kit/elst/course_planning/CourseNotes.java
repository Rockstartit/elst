package edu.kit.elst.course_planning;

import edu.kit.elst.core.shared.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseNotes extends JpaRepository<CourseNote, CourseId> {
}
