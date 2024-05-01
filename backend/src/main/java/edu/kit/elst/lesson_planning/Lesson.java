package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.LessonId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name = "lessons")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Lesson {
    @EmbeddedId
    private final LessonId id;

    @Embedded
    private Topic topic;

    @Embedded
    private Subject subject;

    @Embedded
    private TargetAudience targetAudience;

    @Embedded
    private Schedule schedule;

    @Embedded
    private PriorKnowledge priorKnowledge;

    @Embedded
    private LearningPrerequisites learningPrerequisites;

    @Embedded
    private ThematicAreas thematicAreas;

    @Embedded
    private License license;

    public Lesson(Topic topic) {
        this.id = Lessons.nextIdentity();

        topic(topic);
    }

    public void topic(Topic topic) {
        Guards.notNull(topic, "topic");

        this.topic = topic;
    }

    public Optional<Subject> subject() {
        return Optional.ofNullable(subject);
    }

    public Optional<TargetAudience> targetAudience() {
        return Optional.ofNullable(targetAudience);
    }

    public Optional<Schedule> schedule() {
        return Optional.ofNullable(schedule);
    }

    public Optional<PriorKnowledge> priorKnowledge() {
        return Optional.ofNullable(priorKnowledge);
    }

    public Optional<LearningPrerequisites> learningPrerequisites() {
        return Optional.ofNullable(learningPrerequisites);
    }

    public Optional<ThematicAreas> thematicAreas() {
        return Optional.ofNullable(thematicAreas);
    }

    public Optional<License> license() {
        return Optional.ofNullable(license);
    }
}
