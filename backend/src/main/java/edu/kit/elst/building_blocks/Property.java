package edu.kit.elst.building_blocks;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Property {
    private final String key;
    private final String displayName;
    private final int order;

    public Property(String key, String displayName, int order) {
        Guards.notEmptyBlankOrNull(key, "key");
        Guards.notEmptyBlankOrNull(displayName, "displayName");

        this.key = key;
        this.displayName = displayName;
        this.order = order;
    }
}
