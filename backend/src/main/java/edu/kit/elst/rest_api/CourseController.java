package edu.kit.elst.rest_api;

import edu.kit.elst.course_conceptualization.CourseNotFoundException;
import edu.kit.elst.course_conceptualization.CourseSchedule;
import edu.kit.elst.course_conceptualization.CourseService;
import edut.kit.elst.rest_api.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class CourseController implements CourseApi {
    private final CourseService courseService;

    @Override
    public ResponseEntity<CourseVersion> createCourse(CreateCourseRequest body) {
        edu.kit.elst.course_conceptualization.CourseInformation courseInformation
                = new edu.kit.elst.course_conceptualization.CourseInformation(
                body.getCode(),
                body.getName(),
                body.getCreditPoints(),
                body.getDegree(),
                body.getSemester()
        );

        edu.kit.elst.course_conceptualization.CourseVersion version
                = courseService.createEmptyCourse(courseInformation);

        return ResponseEntity.ok(mapToCourseVersion(version));
    }

    @Override
    public ResponseEntity<Void> deleteCourse(UUID courseId, BigDecimal versionNumber) {
        edu.kit.elst.course_conceptualization.CourseVersion version = mapToCourseVersion(courseId, versionNumber);

        courseService.deleteCourse(version);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> editCourse(UUID courseId, BigDecimal versionNumber, EditCourseRequest body) {
        edu.kit.elst.course_conceptualization.CourseVersion version = mapToCourseVersion(courseId, versionNumber);

        if (body.getInformation() != null) {
            CourseInformation courseInformationBody = body.getInformation();

            edu.kit.elst.course_conceptualization.CourseInformation courseInformation
                    = new edu.kit.elst.course_conceptualization.CourseInformation(
                    courseInformationBody.getCode(),
                    courseInformationBody.getName(),
                    courseInformationBody.getCreditPoints(),
                    courseInformationBody.getDegree(),
                    courseInformationBody.getSemester()
            );

            courseService.editCourseInformation(version, courseInformation);
        }

        if (body.getPrerequisite() != null) {
            edu.kit.elst.course_conceptualization.Prerequisite prerequisite = new edu.kit.elst.course_conceptualization.Prerequisite(
                    body.getPrerequisite().isGradRequired(),
                    body.getPrerequisite().getKnowledge(),
                    body.getPrerequisite().getSkills()
            );

            courseService.editCoursePrerequisite(version, prerequisite);
        }

        if (body.getSchedule() != null) {
            CourseSchedule courseSchedule = new CourseSchedule(body.getSchedule());

            courseService.editCourseSchedule(version, courseSchedule);
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CourseVersion> evolveCourse(UUID courseId, BigDecimal versionNumber) {
        edu.kit.elst.course_conceptualization.CourseVersion version = mapToCourseVersion(courseId, versionNumber);

        edu.kit.elst.course_conceptualization.CourseVersion nextVersion = courseService.evolveCourse(version);

        return ResponseEntity.ok(mapToCourseVersion(nextVersion));
    }

    @Override
    public ResponseEntity<List<Course>> getAllCourses() {
        Collection<edu.kit.elst.course_conceptualization.Course> courses = courseService.courses();

        return ResponseEntity.ok(courses.stream()
                .map(this::mapToCourse)
                .toList());
    }

    @Override
    public ResponseEntity<Course> getCourse(UUID courseId, BigDecimal versionNumber) {
        edu.kit.elst.course_conceptualization.CourseVersion version = mapToCourseVersion(courseId, versionNumber);

        edu.kit.elst.course_conceptualization.Course course = courseService.course(version)
                .orElseThrow(() -> new CourseNotFoundException(version));

        return ResponseEntity.ok(mapToCourse(course));
    }

    private CourseVersion mapToCourseVersion(edu.kit.elst.course_conceptualization.CourseVersion version) {
        CourseVersion dto = new CourseVersion();

        dto.setCourseId(version.courseId());
        dto.setVersion(BigDecimal.valueOf(version.versionNumber()));

        return dto;
    }

    private Course mapToCourse(edu.kit.elst.course_conceptualization.Course course) {
        Course dto = new Course();

        dto.setId(course.version().courseId());
        dto.setVersion(BigDecimal.valueOf(course.version().versionNumber()));

        dto.setCode(course.courseInformation().code());
        dto.setName(course.courseInformation().name());
        dto.setDegree(course.courseInformation().degree().orElse(null));
        dto.setCreditPoints(course.courseInformation().creditPoints().orElse(null));
        dto.setSemester(course.courseInformation().semester().orElse(null));

        course.courseSchedule().ifPresent(courseSchedule -> {
            dto.setSchedule(courseSchedule.value());
        });

        course.prerequisite().ifPresent(prerequisite -> {
            dto.setSkills(prerequisite.skills());
            dto.setGradRequired(prerequisite.gradRequired());
            dto.setKnowledge(prerequisite.knowledge());
        });

        return dto;
    }

    private edu.kit.elst.course_conceptualization.CourseVersion mapToCourseVersion(UUID courseId, BigDecimal versionNumber) {
        return new edu.kit.elst.course_conceptualization.CourseVersion(courseId, versionNumber.longValue());
    }
}
