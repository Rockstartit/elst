package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.CourseId;
import edu.kit.elst.core.shared.LessonId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class CourseAppService {
    private final Courses courses;
    private final CourseNotes courseNotes;

    public CourseId createCourse(LessonId lessonId, TechnologyWish technologyWish) {
        Course course = new Course(lessonId, technologyWish, UserContext.getUserId());

        courses.save(course);

        return course.id();
    }

    public void editTechnologyWish(CourseId courseId, TechnologyWish technologyWish) {
        Course course = course(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        course.technologyWish(technologyWish);
    }

    public void editCourseNotes(CourseId courseId, String content) {
        CourseNote courseNote = courseNotes.findById(courseId)
                .orElse(new CourseNote(courseId));

        courseNote.content(content);

        courseNotes.save(courseNote);
    }

    public void deleteCourse(CourseId courseId) {
        courses.deleteById(courseId);
    }

    public Collection<Course> courses(LessonId lessonId) {
        return courses.findAllByLessonId(lessonId);
    }

    public Optional<Course> course(CourseId courseId) {
        return courses.findById(courseId);
    }

    public Collection<Course> courses() {
        return courses.findAll();
    }

    public CourseNote courseNote(CourseId courseId) {
        return courseNotes.findById(courseId)
                .orElse(new CourseNote(courseId));
    }

    public Collection<Course> courses(Set<CourseId> courseIds) {
        return courses.findAllById(courseIds);
    }
}
