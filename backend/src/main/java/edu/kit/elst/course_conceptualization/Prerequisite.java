package edu.kit.elst.course_conceptualization;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Prerequisite {
    private final boolean gradRequired;
    private final String knowledge;
    private final String skills;

    public Prerequisite(boolean gradRequired, String knowledge, String skills) {
        this.gradRequired = gradRequired;
        this.knowledge = knowledge;
        this.skills = skills;
    }
}
