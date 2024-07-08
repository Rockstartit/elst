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
public class InstructionMethods {
    @Lob
    @Column(name = "instruction_methods", length = 256)
    private final String value;

    public InstructionMethods(String value) {
        this.value = value;
    }
}
