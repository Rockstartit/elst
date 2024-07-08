package edu.kit.elst.lesson_planning;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PriorKnowledge {
    @Lob
    @Column(name = "prior_knowledge", length = 512)
    private final String value;

    public PriorKnowledge(String value) {
        this.value = value;
    }
}
