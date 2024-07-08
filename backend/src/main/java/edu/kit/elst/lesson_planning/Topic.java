package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Topic {
    @Lob
    @Column(name = "topic", length = 256)
    private final String value;

    public Topic(String value) {
        Guards.notEmptyBlankOrNull(value, "value");

        this.value = value;
    }
}
