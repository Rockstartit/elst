package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.TeachingUnitId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name = "teaching_units")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class TeachingUnit {
    @EmbeddedId
    private final TeachingUnitId id;

    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "lesson_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private final Lesson lesson;

    private Topic topic;

    @Embedded
    private RoughContentAnalysis roughContentAnalysis;

    @Embedded
    private DidacticConsideration didacticConsideration;

    @Embedded
    private AcquiredCompetences acquiredCompetences;

    @Embedded
    private CurriculumAlignment curriculumAlignment;

    @Embedded
    private FrameworkConditions frameworkConditions;

    @Embedded
    private InstructionMethods instructionMethods;

    public TeachingUnit(Lesson lesson, Topic topic) {
        Guards.notNull(lesson, "lesson");

        this.id = TeachingUnits.nextIdentity();
        this.lesson = lesson;

        topic(topic);
    }

    public void topic(Topic topic) {
        Guards.notNull(topic, "topic");

        this.topic = topic;
    }

    public Optional<RoughContentAnalysis> roughContentAnalysis() {
        return Optional.ofNullable(roughContentAnalysis);
    }

    public Optional<DidacticConsideration> didacticConsideration() {
        return Optional.ofNullable(didacticConsideration);
    }

    public Optional<AcquiredCompetences> acquiredCompetences() {
        return Optional.ofNullable(acquiredCompetences);
    }

    public Optional<CurriculumAlignment> curriculumAlignment() {
        return Optional.ofNullable(curriculumAlignment);
    }

    public Optional<FrameworkConditions> frameworkConditions() {
        return Optional.ofNullable(frameworkConditions);
    }

    public Optional<InstructionMethods> instructionMethods() {
        return Optional.ofNullable(instructionMethods);
    }
}
