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
public class FrameworkConditions {
    @Lob
    @Column(name = "framework_conditions", length = 256)
    private final String value;

    public FrameworkConditions(String value) {
        this.value = value;
    }
}
