package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.TeachingPhaseId;
import edu.kit.elst.core.shared.TeachingUnitId;
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

    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "teaching_unit_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private final TeachingUnit teachingUnit;

    private Topic topic;

    private Duration timeFrame;

    @Enumerated(EnumType.ORDINAL)
    private LearningCyclePhase phase;

    @Enumerated(EnumType.ORDINAL)
    private TeacherPresence teacherPresence;

    @Column(name = "order_index")
    private long order;

    public TeachingPhase(TeachingUnit teachingUnit, Topic topic) {
        Guards.notNull(teachingUnit, "teachingUnit");

        this.id = TeachingPhases.nextIdentity();
        this.teachingUnit = teachingUnit;
        this.order = 0;

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

    public Optional<TeacherPresence> teacherPresence() {
        return Optional.ofNullable(teacherPresence);
    }

    public TeachingUnitId teachingUnitId() {
        return teachingUnit.id();
    }
}
