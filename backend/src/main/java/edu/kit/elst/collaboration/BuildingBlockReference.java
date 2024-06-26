package edu.kit.elst.collaboration;

import edu.kit.elst.building_blocks.BuildingBlockId;
import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.CourseId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BuildingBlockReference extends DiscussionReference {
    private final BuildingBlockId buildingBlockId;

    public BuildingBlockReference(Discussion discussion, BuildingBlockId buildingBlockId) {
        super(discussion);

        Guards.notNull(buildingBlockId, "buildingBlockId");
        this.buildingBlockId = buildingBlockId;
    }
}
