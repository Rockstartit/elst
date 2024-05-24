package edu.kit.elst.building_blocks;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BuildingBlockProperty {
    private final String key;
    private final String displayName;
    private String description;
    private final int order;

    @Enumerated(EnumType.ORDINAL)
    private BuildingBlockPropertyType type;

    public BuildingBlockProperty(String key, String displayName, int order) {
        Guards.notEmptyBlankOrNull(key, "key");
        Guards.notEmptyBlankOrNull(displayName, "displayName");

        this.key = key;
        this.displayName = displayName;
        this.order = order;
    }
}
