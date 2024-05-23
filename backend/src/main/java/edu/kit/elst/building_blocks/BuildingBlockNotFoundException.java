package edu.kit.elst.building_blocks;

public class BuildingBlockNotFoundException extends RuntimeException {
    public BuildingBlockNotFoundException(BuildingBlockId version) {
        super(String.format("building block %s does not exist", version));
    }
}
