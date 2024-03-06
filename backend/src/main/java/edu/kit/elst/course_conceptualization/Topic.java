package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "course_unit_topics")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Topic {
    @EmbeddedId
    private final TopicId topicId;

    @JoinColumn(name = "course_unit_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CourseUnit courseUnit;

    private String title;
    private LocalDate date;
    private String content;
    private String description;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "topic")
    private Collection<Subtopic> subtopics;

    Topic(CourseUnit courseUnit, String title) {
        Guards.notNull(courseUnit, "courseUnit");
        Guards.notEmptyBlankOrNull(title, "title");

        this.topicId = CourseTopics.nextTopicId();
        this.courseUnit = courseUnit;
        this.title = title;
        this.date = null;
        this.content = null;
        this.description = null;
    }

    public Subtopic addSubtopic(String title) {
        return new Subtopic(this, title);
    }
}
