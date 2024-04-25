package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.Guards;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class TeachingPhase {
    @EmbeddedId
    private final TeachingPhaseId id;

    @JoinColumn
    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private final TeachingUnit teachingUnit;

    private Topic topic;

    private Duration timeFrame;

    private LearningCyclePhase phase;

    public TeachingPhase(TeachingUnit teachingUnit, Topic topic) {
        Guards.notNull(teachingUnit, "teachingUnit");

        this.id = TeachingPhases.nextIdentity();
        this.teachingUnit = teachingUnit;

        topic(topic);
    }

    public void topic(Topic topic) {
        Guards.notNull(topic, "topic");

        this.topic = topic;
    }

    public Optional<Duration> timeFrame() {
        return Optional.ofNullable(timeFrame);
    }

    public Optional<LearningCyclePhase> phase() {
        return Optional.ofNullable(phase);
    }
}
