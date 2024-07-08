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
public class LearningPrerequisites {
    @Lob
    @Column(name = "learning_prerequisites", length = 1024)
    private final String value;

    public LearningPrerequisites(String value) {
        this.value = value;
    }
}
