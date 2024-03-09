package edu.kit.elst.course_conceptualization;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Getter
@Entity
@Table(name = "course_units")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CourseUnit {
    @EmbeddedId
    private final CourseUnitId id;

    @JoinColumn(name = "course_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private final Course course;

    private String description;

    @ElementCollection
    @CollectionTable(
            name = "course_unit_learning_goals",
            joinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "course_id"),
                    @JoinColumn(name = "course_version_number", referencedColumnName = "course_version_number")
            })
    private Collection<LearningGoal> learningGoals;

    @Embedded
    private StudyMaterials studyMaterials;

    CourseUnit(Course course) {
        this.id = CourseUnits.nextIdentity();
        this.course = course;
        this.description = null;
        this.studyMaterials = null;
        this.learningGoals = new ArrayList<>();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLearningGoals(Collection<LearningGoal> learningGoals) {
        this.learningGoals.clear();

        if (learningGoals != null) {
            this.learningGoals.addAll(learningGoals);
        }
    }

    public void setStudyMaterials(StudyMaterials studyMaterials) {
        this.studyMaterials = studyMaterials;
    }

    public Collection<LearningGoal> learningGoals() {
        return Collections.unmodifiableCollection(learningGoals);
    }
}
