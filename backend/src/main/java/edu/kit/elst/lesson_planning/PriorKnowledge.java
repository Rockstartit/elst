package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PriorKnowledge {
    @Column(name = "prior_knowledge", length = 512)
    private final String value;

    public PriorKnowledge(String value) {
        Guards.notEmptyBlankOrNull(value, "value");

        this.value = value;
    }
}
