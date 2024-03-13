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
    private final Pages pages;
    private final CourseTopics courseTopics;

    public CourseUnitId createCourseUnit(CourseVersion version) {
        Course course = courses.getReferenceById(version);

        CourseUnit courseUnit = new CourseUnit(course);
        courseUnits.save(courseUnit);

        return courseUnit.id();
    }

    public void deleteCourseUnit(CourseUnitId courseUnitId) {
        courseTopics.deleteAllByCourseUnitId(courseUnitId);
        pages.deleteAllByCourseUnitId(courseUnitId);
        courseUnits.deleteById(courseUnitId);
    }

    public void editCourseUnit(CourseUnitId courseUnitId,
                               String description,
                               Collection<LearningGoal> learningGoals,
                               StudyMaterials studyMaterials) {
        CourseUnit courseUnit = courseUnits.findById(courseUnitId)
                .orElseThrow(() -> new CourseUnitNotFoundException(courseUnitId));

        courseUnit.setDescription(description);
        courseUnit.setLearningGoals(learningGoals);
        courseUnit.setStudyMaterials(studyMaterials);
    }

    public void deleteAll(CourseVersion version) {
        for (CourseUnit courseUnit : courseUnits.findAllByCourseVersion(version)) {
            deleteCourseUnit(courseUnit.id());
        }
    }
}
