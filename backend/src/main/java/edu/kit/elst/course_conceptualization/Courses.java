package edu.kit.elst.course_conceptualization;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface Courses extends CrudRepository<Course, CourseVersion> {
    default CourseVersion newCourseVersion() {
        return new CourseVersion(UUID.randomUUID(), 1);
    }

    default CourseVersion incrementVersion(CourseVersion courseVersion) {
        Optional<Long> highestVersionOptional = highestVersion(courseVersion.courseId());

        long version = highestVersionOptional
                .map(highestVersion -> highestVersion + 1)
                .orElse(1L);

        return new CourseVersion(courseVersion.courseId(), version);
    }

    @Query("select course.version.versionNumber from Course course " +
            "where course.version.courseId = :courseId")
    Optional<Long> highestVersion(UUID courseId);
}
