package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class FrameworkConditions {
    private final String value;

    public FrameworkConditions(String value) {
        Guards.notEmptyBlankOrNull(value, "value");

        this.value = value;
    }
}
