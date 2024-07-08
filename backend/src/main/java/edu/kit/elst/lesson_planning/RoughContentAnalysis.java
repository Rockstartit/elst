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
public class RoughContentAnalysis {
    @Lob
    @Column(name = "rough_content_analysis", length = 65536)
    private final String value;

    public RoughContentAnalysis(String value) {
        this.value = value;
    }
}
