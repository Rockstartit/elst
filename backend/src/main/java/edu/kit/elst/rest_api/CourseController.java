package edu.kit.elst.rest_api;

import edu.kit.elst.core.shared.CourseId;
import edu.kit.elst.core.shared.LessonId;
import edu.kit.elst.course_conceptualization.CourseAppService;
import edu.kit.elst.course_conceptualization.CourseNotFoundException;
import edu.kit.elst.course_conceptualization.CourseNote;
import edu.kit.elst.course_conceptualization.TechnologyWish;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class CourseController implements CourseApi {
    private final CourseAppService courseAppService;

    @Override
    public ResponseEntity<UUID> createCourse(UUID lessonId, CreateCourseRequest body) {
        LessonId aLessonId = new LessonId(lessonId);
        TechnologyWish technologyWish = new TechnologyWish(body.getTechnologyWish());

        CourseId courseId = courseAppService.createCourse(aLessonId, technologyWish);

        return ResponseEntity.ok(courseId.value());
    }

    @Override
    public ResponseEntity<Void> editCourse(UUID courseId, EditCourseRequest body) {
        CourseId aCourseId = new CourseId(courseId);

        if (body.getNotes() != null) {
            courseAppService.editCourseNotes(aCourseId, body.getNotes());
        }

        if (body.getTechnologyWish() != null) {
            TechnologyWish technologyWish = new TechnologyWish(body.getTechnologyWish());

            courseAppService.editTechnologyWish(aCourseId, technologyWish);
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteCourse(UUID courseId) {
        CourseId aCourseId = new CourseId(courseId);

        courseAppService.deleteCourse(aCourseId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<CourseOverview>> getAllCourses(UUID lessonId) {
        Collection<edu.kit.elst.course_conceptualization.Course> courses;

        if (lessonId != null) {
            LessonId aLessonId = new LessonId(lessonId);

            courses = courseAppService.courses(aLessonId);
        } else {
            courses = courseAppService.courses();
        }

        return ResponseEntity.ok(courses.stream()
                .map(this::mapToCourseOverview)
                .toList());
    }

    @Override
    public ResponseEntity<Course> getCourse(UUID courseId) {
        CourseId aCourseId = new CourseId(courseId);

        edu.kit.elst.course_conceptualization.Course course = courseAppService.course(aCourseId)
                .orElseThrow(() -> new CourseNotFoundException(aCourseId));

        CourseNote notes = courseAppService.courseNote(aCourseId);

        return ResponseEntity.ok(mapToCourse(course, notes));
    }

    private Course mapToCourse(edu.kit.elst.course_conceptualization.Course course, CourseNote notes) {
        Course dto = new Course();

        dto.setId(course.id().value());
        dto.setLessonId(course.lessonId().value());
        dto.setTechnologyWish(course.technologyWish().value());
        dto.setNotes(notes.content());

        return dto;
    }

    private CourseOverview mapToCourseOverview(edu.kit.elst.course_conceptualization.Course course) {
        CourseOverview dto = new CourseOverview();

        dto.setId(course.id().value());
        dto.setLessonId(course.lessonId().value());
        dto.setTechnologyWish(course.technologyWish().value());

        return dto;
    }
}
