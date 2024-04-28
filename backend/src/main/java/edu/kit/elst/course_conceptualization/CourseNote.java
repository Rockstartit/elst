package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Entity
@Getter
@Table(name = "course_notes")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CourseNote {
    @EmbeddedId
    private final CourseId courseId;

    @Lob
    private byte[] content;

    public CourseNote(CourseId courseId) {
        this.courseId = courseId;
        this.content = new byte[0];
    }

    public void data(String content) {
        Guards.notNull(content, "content");

        this.content = content.getBytes(StandardCharsets.UTF_8);
    }

    public void content(String content) {
        this.content = content.getBytes();
    }

    public String content() {
        return new String(content);
    }
}
