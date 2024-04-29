package edu.kit.elst.rest_api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class CourseController implements CourseApi {
    @Override
    public ResponseEntity<UUID> createCourse(UUID lessonId, CreateCourseRequest body) {
        return CourseApi.super.createCourse(lessonId, body);
    }

    @Override
    public ResponseEntity<Void> editCourse(UUID courseId, EditCourseRequest body) {
        return CourseApi.super.editCourse(courseId, body);
    }

    @Override
    public ResponseEntity<Void> deleteCourse(UUID courseId) {
        return CourseApi.super.deleteCourse(courseId);
    }

    @Override
    public ResponseEntity<List<Course>> getAllCourses() {
        return CourseApi.super.getAllCourses();
    }

    @Override
    public ResponseEntity<Course> getCourse(UUID courseId) {
        return CourseApi.super.getCourse(courseId);
    }
}
