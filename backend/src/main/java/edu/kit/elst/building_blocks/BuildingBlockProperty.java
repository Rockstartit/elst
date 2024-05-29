package edu.kit.elst.building_blocks;

import edu.kit.elst.core.Guards;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "building_block_properties")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BuildingBlockProperty {
    @EmbeddedId
    private final BuildingBlockPropertyId id;

    private final String displayName;
    private String description;
    private final int order;

    @Enumerated(EnumType.ORDINAL)
    private BuildingBlockPropertyType type;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "building_block_id", insertable = false, updatable = false)
    private BuildingBlock buildingBlock;

    public BuildingBlockProperty(BuildingBlock buildingBlock, String key, String displayName) {
        Guards.notNull(buildingBlock, "buildingBlock");
        Guards.notEmptyBlankOrNull(displayName, "displayName");

        this.id = new BuildingBlockPropertyId(buildingBlock.id(), key);
        this.buildingBlock = buildingBlock;
        this.displayName = displayName;
        this.order = 0;
    }

    public String key() {
        return id.key();
    }

    public BuildingBlockId buildingBlockId() {
        return id.buildingBlockId();
    }
}
