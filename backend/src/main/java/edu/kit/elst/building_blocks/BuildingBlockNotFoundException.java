package edu.kit.elst.building_blocks;

import edu.kit.elst.core.shared.BuildingBlockId;

public class BuildingBlockNotFoundException extends RuntimeException {
    public BuildingBlockNotFoundException(BuildingBlockId id) {
        super(String.format("building block %s does not exist", id));
    }
}
