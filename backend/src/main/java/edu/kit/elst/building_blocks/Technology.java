package edu.kit.elst.building_blocks;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Technology {
    @Column(name = "technology")
    private final String value;

    public Technology(String value) {
        Guards.notEmptyBlankOrNull(value, "value");

        this.value = value;
    }
}
