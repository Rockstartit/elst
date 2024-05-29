package edu.kit.elst.building_blocks;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BuildingBlockPropertyId implements Serializable {
    @Column(name = "building_block_id", nullable = false)
    private final UUID buildingBlockId;

    @Column(name = "key", nullable = false)
    private final String key;

    public BuildingBlockPropertyId(BuildingBlockId buildingBlockId, String key) {
        Guards.notNull(buildingBlockId, "buildingBlockId");
        Guards.notNull(key, "key");

        this.buildingBlockId = buildingBlockId.value();
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingBlockPropertyId id = (BuildingBlockPropertyId) o;
        return Objects.equals(buildingBlockId, id.buildingBlockId) && Objects.equals(key, id.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingBlockId, key);
    }

    @Override
    public String toString() {
        return buildingBlockId.toString() + "#" + key;
    }

    public BuildingBlockId buildingBlockId() {
        return new BuildingBlockId(buildingBlockId);
    }
}
