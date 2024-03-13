package edu.kit.elst.building_blocks;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BuildingBlockDetails {
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    private String description;

    public BuildingBlockDetails(String name, String description) {
        Guards.notEmptyBlankOrNull(name, "name");

        this.name = name;
        this.description = Objects.requireNonNullElse(description, "");;
    }

    public BuildingBlockDetails(String name) {
        this(name, "");
    }

    public BuildingBlockDetails setName(String name) {
        return new BuildingBlockDetails(name, description);
    }

    public BuildingBlockDetails setDescription(String description) {
        return new BuildingBlockDetails(name, description);
    }
}
