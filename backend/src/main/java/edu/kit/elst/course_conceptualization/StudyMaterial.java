package edu.kit.elst.course_conceptualization;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class StudyMaterial {
    private final String eReading;
    private final String eBook;
    private final String bibliography;
    private final String relatedLinks;

    public StudyMaterial(String eReading, String eBook, String bibliography, String relatedLinks) {
        this.eReading = eReading;
        this.eBook = eBook;
        this.bibliography = bibliography;
        this.relatedLinks = relatedLinks;
    }
}
