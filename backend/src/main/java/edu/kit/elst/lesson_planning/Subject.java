package edu.kit.elst.lesson_planning;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Subject {
    @Column(name = "subject")
    private final String value;

    public Subject(String value) {
        this.value = value;
    }
}
