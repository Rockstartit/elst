package edu.kit.elst.lesson_planning;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class RoughContentAnalysis {
    @Column(name = "rough_content_analysis", length = 4048)
    private final String value;

    public RoughContentAnalysis(String value) {
        this.value = value;
    }
}
