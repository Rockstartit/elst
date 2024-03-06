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
@Table(name = "course_unit_sub_topics")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Subtopic {
    @EmbeddedId
    private final TopicId subtopicId;

    @JoinColumn(name = "topic_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Topic topic;

    private String title;
    private LocalDate date;
    private String content;
    private String description;

    Subtopic(Topic topic, String title) {
        Guards.notNull(topic, "topic");
        Guards.notEmptyBlankOrNull(title, "title");

        this.subtopicId = CourseTopics.nextSubtopicId();
        this.topic = topic;
        this.title = title;
        this.date = null;
        this.content = null;
        this.description = null;
    }
}
