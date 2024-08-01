package edu.kit.elst.course_planning;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class TechnologyWish {
    @Column(name = "technology_wish")
    private final String value;

    public TechnologyWish(String value) {
        Guards.notEmptyBlankOrNull(value, "value");

        this.value = value;
    }
}
