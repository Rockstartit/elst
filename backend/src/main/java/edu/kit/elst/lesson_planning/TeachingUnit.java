package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.Guards;
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private final Lesson lesson;

    private Topic topic;

    @Embedded
    private RoughContentAnalysis roughContentAnalysis;

    @Embedded
    private DidacticConsideration didacticConsideration;

    @Embedded
    private AcquiredCompetences acquiredCompetences;

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
}
