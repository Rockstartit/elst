package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.UserContext;
import edu.kit.elst.lesson_planning.LessonId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CourseAppService {
    private final Courses courses;

    public CourseId createCourse(LessonId lessonId, TechnologyWish technologyWish) {
        Course course = new Course(lessonId, technologyWish, UserContext.getUserId());

        courses.save(course);

        return course.id();
    }

    public void editCourse(CourseId courseId, TechnologyWish technologyWish) {
        Course course = course(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        course.technologyWish(technologyWish);
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
}
