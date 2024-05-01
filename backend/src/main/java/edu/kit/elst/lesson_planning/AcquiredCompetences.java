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
public class AcquiredCompetences {
    @Column(name = "acquired_competences", length = 1024)
    private final String value;

    public AcquiredCompetences(String value) {
        this.value = value;
    }
}
