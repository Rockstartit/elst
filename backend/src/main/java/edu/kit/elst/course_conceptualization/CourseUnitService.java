package edu.kit.elst.course_conceptualization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
@AllArgsConstructor
public class CourseUnitService {
    private final Courses courses;
    private final CourseUnits courseUnits;
    private final CourseTopics courseTopics;

    public CourseUnitId addCourseUnit(CourseVersion version) {
        Course course = courses.findById(version)
                .orElseThrow(() -> new CourseNotFoundException(version));

        CourseUnit courseUnit = course.addUnit();
        courseUnits.save(courseUnit);

        return courseUnit.id();
    }

    public void removeCourseUnit(CourseUnitId courseUnitId) {
        courseTopics.deleteAllByCourseUnitId(courseUnitId);
        courseUnits.deleteById(courseUnitId);
    }

    public void editCourseUnit(CourseUnitId courseUnitId,
                               String description,
                               Collection<LearningGoal> learningGoals,
                               Collection<StudyMaterial> studyMaterials) {
        CourseUnit courseUnit = courseUnits.findById(courseUnitId)
                .orElseThrow(() -> new CourseUnitNotFoundException(courseUnitId));

        courseUnit.setDescription(description);
        courseUnit.setLearningGoals(learningGoals);
        courseUnit.setStudyMaterials(studyMaterials);
    }
}
