package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CourseInformation {
    private final String code;
    private final String name;
    private final String creditPoints;
    private final String degree;
    private final String semester;

    public CourseInformation(String code, String name, String creditPoints, String degree, String semester) {
        Guards.notEmptyBlankOrNull(code, "code");
        Guards.notEmptyBlankOrNull(name, "name");

        this.code = code;
        this.name = name;
        this.creditPoints = creditPoints;
        this.degree = degree;
        this.semester = semester;
    }
}
