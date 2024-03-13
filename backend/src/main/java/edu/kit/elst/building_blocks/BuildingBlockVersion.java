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
public class BuildingBlockVersion implements Serializable {
    @Column(name = "building_block_id", nullable = false)
    private final UUID buildingBlockId;

    @Column(name = "building_block_version_number", nullable = false)
    private final long versionNumber;

    public BuildingBlockVersion(UUID buildingBlockId, long versionNumber) {
        Guards.notNull(buildingBlockId, "buildingBlockId");

        if (versionNumber <= 0) {
            throw new IllegalArgumentException("building block version cannot be less than 1");
        }

        this.buildingBlockId = buildingBlockId;
        this.versionNumber = versionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingBlockVersion version = (BuildingBlockVersion) o;
        return versionNumber == version.versionNumber && Objects.equals(buildingBlockId, version.buildingBlockId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingBlockId, versionNumber);
    }

    @Override
    public String toString() {
        return buildingBlockId.toString() + "#" + versionNumber;
    }
}
