package edu.kit.elst.course_planning;

import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.PageBuildingBlockId;
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
public class PageBuildingBlockPropertyId implements Serializable {
    @Column(name = "page_building_block_id", nullable = false)
    private final UUID pageBuildingBlockId;

    @Column(name = "key_name", nullable = false)
    private final String key;

    public PageBuildingBlockPropertyId(PageBuildingBlockId pageBuildingBlockId, String key) {
        Guards.notNull(pageBuildingBlockId, "pageBuildingBlockId");
        Guards.notNull(key, "key");

        this.pageBuildingBlockId = pageBuildingBlockId.value();
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageBuildingBlockPropertyId id = (PageBuildingBlockPropertyId) o;
        return Objects.equals(pageBuildingBlockId, id.pageBuildingBlockId) && Objects.equals(key, id.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageBuildingBlockId, key);
    }

    @Override
    public String toString() {
        return pageBuildingBlockId.toString() + "#" + key;
    }

    public BuildingBlockId buildingBlockId() {
        return new BuildingBlockId(pageBuildingBlockId);
    }
}
