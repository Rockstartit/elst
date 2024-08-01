package edu.kit.elst.course_planning;

import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.CourseId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "course_notes")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CourseNote {
    @EmbeddedId
    private final CourseId courseId;

    @Lob
    @Column(length = 65536)
    private String content;

    public CourseNote(CourseId courseId) {
        this.courseId = courseId;
        this.content = "";
    }

    public void data(String content) {
        Guards.notNull(content, "content");

        this.content = content;
    }

    public void content(String content) {
        this.content = content;
    }

    public String content() {
        return content;
    }
}
