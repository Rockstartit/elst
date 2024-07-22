package edu.kit.elst.communication;

import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.core.Guards;
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
