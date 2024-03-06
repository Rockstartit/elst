package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class LearningGoal {
    private final String value;

    public LearningGoal(String value) {
        Guards.notEmptyBlankOrNull(value, "value");

        this.value = value;
    }
}
